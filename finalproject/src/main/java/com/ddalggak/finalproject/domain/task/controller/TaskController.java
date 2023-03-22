package com.ddalggak.finalproject.domain.task.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddalggak.finalproject.domain.task.dto.TaskRequestDto;
import com.ddalggak.finalproject.domain.task.service.TaskService;
import com.ddalggak.finalproject.global.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Task Controller", description = "Task 관련 API입니다.")
@RestController
@RequestMapping("/api/{projectId}")
@RequiredArgsConstructor
public class TaskController {
	private final TaskService taskService;

	@PostMapping("/task")
	@Operation(summary = "Task 생성", description = "api for creating task")
	public ResponseEntity<?> createTask(
		@PathVariable Long projectId,
		@AuthenticationPrincipal UserDetailsImpl user,
		@RequestBody TaskRequestDto taskRequestDto) {
		return taskService.createTask(projectId, user.getEmail(), taskRequestDto);
	}
}
