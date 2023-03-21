package com.ddalggak.finalproject.domain.task.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ddalggak.finalproject.domain.project.entity.Project;
import com.ddalggak.finalproject.domain.project.repository.ProjectRepository;
import com.ddalggak.finalproject.domain.task.dto.TaskRequestDto;
import com.ddalggak.finalproject.domain.task.dto.TaskUserRequestDto;
import com.ddalggak.finalproject.domain.task.entity.Task;
import com.ddalggak.finalproject.domain.task.entity.TaskUser;
import com.ddalggak.finalproject.domain.task.repository.TaskRepository;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.domain.user.exception.UserException;
import com.ddalggak.finalproject.domain.user.repository.UserRepository;
import com.ddalggak.finalproject.global.dto.SuccessCode;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;
import com.ddalggak.finalproject.global.error.CustomException;
import com.ddalggak.finalproject.global.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository taskRepository;

	private final UserRepository userRepository;

	private final ProjectRepository projectRepository;

	public ResponseEntity<?> createTask(Long projectId, String email, TaskRequestDto taskRequestDto) {
		User user = userRepository.findByEmail(email).orElseThrow(
			() -> new UserException(ErrorCode.MEMBER_NOT_FOUND)
		);
		Project project = projectRepository.findById(projectId).orElseThrow(
			() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
		);
		TaskUserRequestDto taskUserRequestDto = TaskUserRequestDto.create(user);
		TaskUser taskUser = TaskUser.create(taskUserRequestDto);
		Task task = Task.create(taskRequestDto, taskUser, project);
		taskRepository.save(task);

		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	}
}
