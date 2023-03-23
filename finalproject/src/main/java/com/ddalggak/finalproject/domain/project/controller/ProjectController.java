package com.ddalggak.finalproject.domain.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddalggak.finalproject.domain.project.dto.ProjectBriefResponseDto;
import com.ddalggak.finalproject.domain.project.dto.ProjectRequestDto;
import com.ddalggak.finalproject.domain.project.service.ProjectService;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;
import com.ddalggak.finalproject.global.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
	public ResponseEntity<SuccessResponseDto> createProject(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody ProjectRequestDto projectRequestDto) {
		return projectService.createProject(userDetails.getUser(), projectRequestDto);
	}

	@Operation(summary = "프로젝트 전체조회", description = "api for view all projects")
	@GetMapping("/projects")
	public ResponseEntity<List<ProjectBriefResponseDto>> viewProjectAll(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return projectService.viewProjectAll(userDetails.getUser());
	}

	@Operation(summary = "프로젝트 단건조회", description = "api for view one project")
	@GetMapping("/project/{projectId}")
	public ResponseEntity<?> viewProjectOne(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId) {
		return projectService.viewProject(userDetails.getUser(), projectId);
	}

	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "프로젝트 참여 성공"),
			@ApiResponse(responseCode = "400", description = "프로젝트 참여 실패 - 이미 있는 사용자"),
			@ApiResponse(responseCode = "403", description = "프로젝트 참여 실패 - 권한 없음"),
			@ApiResponse(responseCode = "404", description = "프로젝트 참여 실패(존재하지 않는 프로젝트)")
		}
	)
	@Operation(summary = "프로젝트 참여", description = "api for join project")
	@PostMapping("/project/{projectId}/join")
	public ResponseEntity<?> joinProject(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId) {
		return projectService.joinProject(userDetails.getUser(), projectId);
	}

	@Operation(summary = "프로젝트 수정", description = "api for update project")
	@PostMapping("/project/{projectId}/settings")
	public ResponseEntity<SuccessResponseDto> updateProject(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId,
		@RequestBody ProjectRequestDto projectRequestDto) {
		return projectService.updateProject(userDetails.getUser(), projectId, projectRequestDto);
	}

	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "201", description = "프로젝트 삭제 성공"),
			@ApiResponse(responseCode = "400", description = "프로젝트 삭제 실패"),
			@ApiResponse(responseCode = "403", description = "프로젝트 삭제 권한 없음"),
			@ApiResponse(responseCode = "404", description = "프로젝트 삭제 실패(존재하지 않는 프로젝트)")
		}
	)
	@Operation(summary = "프로젝트 삭제", description = "api for delete project")
	@DeleteMapping("/project/{projectId}")
	public ResponseEntity<?> deleteProject(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId) {
		return projectService.deleteProject(userDetails.getUser(), projectId);
	}

	@Operation(summary = "프로젝트 참여자 조회", description = "api for view project members")
	@GetMapping("/project/{projectId}/users")
	public ResponseEntity<?> viewProjectUsers(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId) {
		return projectService.viewProjectUsers(userDetails.getUser(), projectId);
	}

	@Operation(summary = "프로젝트 참여자 삭제", description = "api for delete project member")
	@DeleteMapping("/project/{projectId}/user/{userId}")
	public ResponseEntity<?> deleteProjectUser(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@PathVariable Long projectId,
		@PathVariable Long userId) {
		return projectService.deleteProjectUser(userDetails.getUser(), projectId, userId);
	}
}
