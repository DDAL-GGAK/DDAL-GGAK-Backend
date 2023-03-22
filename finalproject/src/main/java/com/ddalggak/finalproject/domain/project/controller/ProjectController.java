package com.ddalggak.finalproject.domain.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddalggak.finalproject.domain.project.dto.ProjectRequestDto;
import com.ddalggak.finalproject.domain.project.service.ProjectService;
import com.ddalggak.finalproject.global.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Project Controller", description = "프로젝트 관련 API입니다.")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectController {

	private final ProjectService projectService;

	@Operation(summary = "프로젝트 생성", description = "api for creating project")
	@PostMapping("/project")
	public ResponseEntity<?> createProject(
		@AuthenticationPrincipal UserDetailsImpl user,
		@RequestBody ProjectRequestDto projectRequestDto) {
		return projectService.createProject(user.getUser().getEmail(), projectRequestDto);
	}

	@Operation(summary = "프로젝트 전체조회", description = "api for view all projects")
	@GetMapping("/projects")
	public ResponseEntity<?> viewProjectAll(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return projectService.viewProjectAll(userDetails.getUser().getEmail());
	}

	@Operation(summary = "프로젝트 단건조회", description = "api for view one project")
	@GetMapping("/project/{projectId}")
	public ResponseEntity<?> viewProjectOne(
		@PathVariable Long projectId) {
		return projectService.viewProject(projectId);
	}

	@Operation(summary = "프로젝트 참여", description = "api for join project")
	@PostMapping("/project/{projectId}/join")
	public ResponseEntity<?> joinProject(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId) {
		return projectService.joinProject(userDetails.getUser().getEmail(), projectId);
	}

	@Operation(summary = "프로젝트 삭제", description = "api for delete project")
	@PostMapping("/project/{projectId}")
	public ResponseEntity<?> deleteProject(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId) {
		return projectService.deleteProject(userDetails.getUser().getEmail(), projectId);
	}
}
