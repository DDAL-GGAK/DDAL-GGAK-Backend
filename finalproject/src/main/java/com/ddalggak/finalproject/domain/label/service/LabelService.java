package com.ddalggak.finalproject.domain.label.service;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddalggak.finalproject.domain.label.dto.LabelRequestDto;
import com.ddalggak.finalproject.domain.label.dto.LabelUserRequestDto;
import com.ddalggak.finalproject.domain.label.entity.Label;
import com.ddalggak.finalproject.domain.label.entity.LabelUser;
import com.ddalggak.finalproject.domain.label.repository.LabelRepository;
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
public class LabelService {
	private final TaskRepository taskRepository;

	private final UserRepository userRepository;

	private final LabelRepository labelRepository;

	@Transactional
	public ResponseEntity<SuccessResponseDto> createLabel(User user, LabelRequestDto labelRequestDto) {
		Task task = validateTask(labelRequestDto.getTaskId());
		validateExistMember(task, TaskUser.create(task, user));
		if (!(task.getProject().getProjectLeader().equals(user.getEmail()) ||
			task.getProject().getTaskLeadersList().contains(user.getEmail()) ||
			task.getLabelLeadersList().contains(user.getEmail()))) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
		LabelUserRequestDto labelUserRequestDto = LabelUserRequestDto.create(user);
		LabelUser labelUser = LabelUser.create(labelUserRequestDto);
		Label label = Label.create(labelRequestDto, labelUser, task);
		labelRepository.save(label);
		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	}

	@Transactional
	public ResponseEntity<SuccessResponseDto> deleteLabel(User user, LabelRequestDto labelRequestDto, Long labelId) {
		Task task = validateTask(labelRequestDto.getTaskId());
		Label label = validateLabel(labelId);
		if (task.getProject().getProjectLeader().equals(user.getEmail()) ||
			Objects.equals(task.getTaskLeader(), user.getEmail()) ||
			Objects.equals(label.getLabelLeader(), user.getEmail())) {
			task.deleteLabelLeader(label);
			labelRepository.delete(label);
		} else {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
		return SuccessResponseDto.toResponseEntity(SuccessCode.DELETED_SUCCESSFULLY);
	}

	@Transactional
	public ResponseEntity<SuccessResponseDto> inviteLabel(User user, LabelRequestDto labelRequestDto, Long labelId) {
		Task task = validateTask(labelRequestDto.getTaskId());
		Label label = validateLabel(labelId);
		User invitedUser = userRepository.findByEmail(labelRequestDto.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
		LabelUser labelUser = LabelUser.create(LabelUserRequestDto.create(invitedUser));
		if (!(task.getProject().getProjectLeader().equals(user.getEmail()) ||
			Objects.equals(task.getTaskLeader(), user.getEmail()) ||
			Objects.equals(label.getLabelLeader(), user.getEmail()))) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		} else if (label.getLabelUserList().contains(labelUser)) {
			throw new CustomException(ErrorCode.DUPLICATE_MEMBER);
		} else {
			validateExistMember(task, TaskUser.create(task, invitedUser));
			label.addLabelUser(labelUser);
			labelRepository.save(label);
		}
		label.addLabelUser(labelUser);
		return SuccessResponseDto.toResponseEntity(SuccessCode.JOINED_SUCCESSFULLY);
	}

	public ResponseEntity<SuccessResponseDto> manageLeader(User user, LabelRequestDto labelRequestDto, Long labelId) {
		User userToManage = validateUserByEmail(labelRequestDto.getEmail());
		Task task = validateTask(labelRequestDto.getTaskId());
		Label label = validateLabel(labelId);
		validateExistMember(label, LabelUser.create(label, userToManage));
		if (task.getProject().getProjectLeader().equals(user.getEmail()) ||
			Objects.equals(task.getTaskLeader(), user.getEmail())) {
			if (label.getLabelLeader() == null) {
				label.setLabelLeader(userToManage.getEmail());
				task.getLabelLeadersList().add(userToManage.getEmail());
			} else if (!label.getLabelLeader().equals(userToManage.getEmail())) {
				label.setLabelLeader(userToManage.getEmail());
				task.getLabelLeadersList().remove(label.getLabelLeader());
				task.getLabelLeadersList().add(userToManage.getEmail());
			} else {
				label.setLabelLeader(null);
				task.getLabelLeadersList().remove(userToManage.getEmail());
			}
		} else {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
		return SuccessResponseDto.toResponseEntity(SuccessCode.UPDATED_SUCCESSFULLY);
	}

	private User validateUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(
			() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND)
		);
	}

	private void validateExistMember(Task task, TaskUser taskUser) {
		if (!task.getTaskUserList().contains(taskUser)) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
	}

	private void validateExistMember(Label label, LabelUser labelUser) {
		if (!label.getLabelUserList().contains(labelUser)) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
	}

	private Task validateTask(Long id) { //todo AOP 적용
		return taskRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.TASK_NOT_FOUND)
		);
	}

	private Label validateLabel(Long id) { // todo LabelRepository 삭제하고 연관관계만으로 긁어오게 만들기
		return labelRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.LABEL_NOT_FOUND)
		);
	}
}
