package com.ddalggak.finalproject.domain.task.service;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddalggak.finalproject.domain.project.entity.Project;
import com.ddalggak.finalproject.domain.project.entity.ProjectUser;
import com.ddalggak.finalproject.domain.project.repository.ProjectRepository;
import com.ddalggak.finalproject.domain.task.dto.TaskRequestDto;
import com.ddalggak.finalproject.domain.task.dto.TaskResponseDto;
import com.ddalggak.finalproject.domain.task.dto.TaskUserRequestDto;
import com.ddalggak.finalproject.domain.task.entity.Task;
import com.ddalggak.finalproject.domain.task.entity.TaskUser;
import com.ddalggak.finalproject.domain.task.repository.TaskRepository;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.domain.user.repository.UserRepository;
import com.ddalggak.finalproject.global.dto.SuccessCode;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;
import com.ddalggak.finalproject.global.error.CustomException;
import com.ddalggak.finalproject.global.error.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository taskRepository;
	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;

	@Transactional
	public ResponseEntity<?> createTask(User user, TaskRequestDto taskRequestDto) {
		Project project = validateProject(taskRequestDto.projectId);
		validateExistMember(project, ProjectUser.create(project, user));
		if (!(project.getProjectLeader().equals(user.getEmail()) || project.getTaskLeadersList()
			.contains(user.getEmail()))) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
		TaskUserRequestDto taskUserRequestDto = TaskUserRequestDto.create(user);
		TaskUser taskUser = TaskUser.create(taskUserRequestDto);
		Task task = Task.create(taskRequestDto, taskUser, project);
		task.setTaskLeader(user.getEmail());
		task.getProject().getTaskLeadersList().add(user.getEmail());
		taskRepository.save(task);
		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	}

	@Transactional(readOnly = true) // project member면 누구나 task 조회 가능하다. task 멤버가 아닐지라도.
	public ResponseEntity<TaskResponseDto> viewTask(User user, Long projectId, Long taskId) {
		Task task = validateTask(taskId);
		Project project = validateProject(projectId);
		validateExistMember(project, ProjectUser.create(project, user));
		return TaskResponseDto.toResponseEntity(task);
	}

	@Transactional
	public ResponseEntity<SuccessResponseDto> deleteTask(User user, Long taskId) {
		Task task = validateTask(taskId);
		if (task.getProject().getProjectLeader().equals(user.getEmail()) ||
			Objects.equals(task.getTaskLeader(), user.getEmail())) {
			task.getProject().deleteTaskLeader(task);
			taskRepository.delete(task);
		} else {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
		return SuccessResponseDto.toResponseEntity(SuccessCode.DELETED_SUCCESSFULLY);
	}

	/*
	 * 프로젝트 리더이거나 taskLeader인 경우만 가능,
	 * 혹시 다른 taskLeader는 초대못할경우 task.getTaskLeader().equals(user.getEmail())로 검증
	 */
	@Transactional
	public ResponseEntity<SuccessResponseDto> inviteTask(User user, TaskRequestDto taskRequestDto, Long taskId) {
		Task task = validateTask(taskId);
		Project project = validateProject(taskRequestDto.getProjectId());
		User invited = userRepository.findByEmail(taskRequestDto.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
			);
		TaskUser taskUser = TaskUser.create(task, invited);
		// 유효성 검증
		if (task.getTaskUserList().contains(taskUser)) {
			throw new CustomException(ErrorCode.DUPLICATE_MEMBER);
		} else if (project.getProjectLeader().equals(user.getEmail()) || project.getTaskLeadersList()
			.contains(user.getEmail())) {
			validateExistMember(project, ProjectUser.create(project, invited)); // user가 프로젝트에 있는 유저인지 검증
			task.addTaskUser(taskUser); // task에 넣기
			taskRepository.save(task);
		} else {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
		return SuccessResponseDto.toResponseEntity(SuccessCode.JOINED_SUCCESSFULLY);
	}

	@Transactional // task 리더면 일반인으로, 반대는 리더로
	public ResponseEntity<SuccessResponseDto> manageLeader(User user, TaskRequestDto taskRequestDto, Long taskId) {
		User userToManage = validateUserByEmail(taskRequestDto.getEmail());
		Task task = validateTask(taskId);
		validateExistMember(task, TaskUser.create(task, userToManage));
		if (task.getProject().getProjectLeader().equals(user.getEmail())) {
			if (task.getTaskLeader() == null) {
				task.setTaskLeader(userToManage.getEmail());
				task.getProject().getTaskLeadersList().add(userToManage.getEmail());
			} else if (!task.getTaskLeader().equals(userToManage.getEmail())) {
				task.setTaskLeader(userToManage.getEmail());
				task.getProject().getTaskLeadersList().remove(task.getTaskLeader());
				task.getProject().getTaskLeadersList().add(userToManage.getEmail());
			} else {
				task.getProject().getTaskLeadersList().remove(userToManage.getEmail());
				task.setTaskLeader(null);
			}
		} else {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
		return SuccessResponseDto.toResponseEntity(SuccessCode.UPDATED_SUCCESSFULLY);
	}

	private void validateExistMember(Task task, TaskUser taskUser) {
		if (!task.getTaskUserList().contains(taskUser)) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
	}

	private void validateExistMember(Project project, ProjectUser projectUser) {
		if (!project.getProjectUserList().contains(projectUser)) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
	}

	private User validateUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(
			() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
		);
	}

	private Project validateProject(Long id) {
		return projectRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
		);
	}

	private Task validateTask(Long id) {
		return taskRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.TASK_NOT_FOUND)
		);
	}

}
