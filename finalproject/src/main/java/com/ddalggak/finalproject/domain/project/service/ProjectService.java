package com.ddalggak.finalproject.domain.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddalggak.finalproject.domain.project.dto.ProjectBriefResponseDto;
import com.ddalggak.finalproject.domain.project.dto.ProjectRequestDto;
import com.ddalggak.finalproject.domain.project.dto.ProjectResponseDto;
import com.ddalggak.finalproject.domain.project.dto.ProjectUserRequestDto;
import com.ddalggak.finalproject.domain.project.entity.Project;
import com.ddalggak.finalproject.domain.project.entity.ProjectUser;
import com.ddalggak.finalproject.domain.project.repository.ProjectRepository;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.domain.user.exception.UserException;
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
@Transactional
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;

	@Transactional
	public ResponseEntity<?> createProject(String email, ProjectRequestDto projectRequestDto) {
		User user = userRepository.findByEmail(email).orElseThrow(
			() -> new UserException(ErrorCode.MEMBER_NOT_FOUND)
		);
		//1. user로 projectUserRequestDto 생성
		ProjectUserRequestDto projectUserRequestDto = ProjectUserRequestDto.create(user);
		//2. projectUserDto로 projectUser생성
		ProjectUser projectUser = ProjectUser.create(projectUserRequestDto);
		//3. projectUser로 project생성
		Project project = Project.create(projectRequestDto, projectUser);
		//4. projectRepository에 project 저장
		projectRepository.save(project);

		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<?> viewProjectAll(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(
			() -> new UserException(ErrorCode.MEMBER_NOT_FOUND)
		);

		List<ProjectBriefResponseDto> projectResponseDtoList = projectRepository.findAllByUserId(user.getUserId())
			.stream()
			.map(ProjectBriefResponseDto::new)
			.collect(Collectors.toList());

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(projectResponseDtoList);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<?> viewProject(Long id) {
		Project project = projectRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
		);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ProjectResponseDto.of(project));
	}

	@Transactional
	public ResponseEntity<?> deleteProject(String email, Long projectId) {
		Project project = projectRepository.findById(projectId).orElseThrow(
			() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
		);
		if (project.getCreatedBy().equals(email)) {
			projectRepository.delete(project);
			return SuccessResponseDto.toResponseEntity(SuccessCode.DELETED_SUCCESSFULLY);
		} else {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
	}

	@Transactional
	public ResponseEntity<?> joinProject(String email, Long projectId) {
		User user = userRepository.findByEmail(email).orElseThrow(
			() -> new UserException(ErrorCode.MEMBER_NOT_FOUND)
		);
		Project project = projectRepository.findById(projectId).orElseThrow(
			() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND)
		);
		validateDuplicateMember(project, user);
		ProjectUserRequestDto projectUserRequestDto = ProjectUserRequestDto.join(project, user);
		ProjectUser projectUser = ProjectUser.create(projectUserRequestDto);
		project.addProjectUser(projectUser);
		return SuccessResponseDto.toResponseEntity(SuccessCode.JOINED_SUCCESSFULLY);
	}

	//todo : 테스트 코드 작성, 로직 수정
	private void validateDuplicateMember(Project project, User user) {
		if (project.getProjectUserList().contains(user)) {
			throw new CustomException(ErrorCode.DUPLICATE_MEMBER);
		}
	}
}
