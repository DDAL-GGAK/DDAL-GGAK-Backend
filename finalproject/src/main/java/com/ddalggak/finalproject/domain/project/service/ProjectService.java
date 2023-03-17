package com.ddalggak.finalproject.domain.project.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddalggak.finalproject.domain.project.dto.ProjectRequestDto;
import com.ddalggak.finalproject.domain.project.entity.Project;
import com.ddalggak.finalproject.domain.project.repository.ProjectRepository;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.domain.user.repository.UserRepository;
import com.ddalggak.finalproject.global.dto.SuccessCode;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;

	private final UserRepository userRepository;

	@Transactional
	public ResponseEntity<?> createProject(User user, ProjectRequestDto projectRequestDto) {
		projectRepository.save(new Project(projectRequestDto));
		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	}

	// @Transactional(readOnly = true)
	// public ResponseEntity<?> viewProjectAll(String email) {
	// 	User user = userRepository.findByEmail(email).orElseThrow(
	// 		() -> new UserException(ErrorCode.MEMBER_NOT_FOUND)
	// 	);
	// 	List<Project> projectList = projectRepository.findAllById(user.getUserId());
	// }
}
