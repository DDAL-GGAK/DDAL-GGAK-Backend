package com.ddalggak.finalproject.domain.label.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddalggak.finalproject.domain.label.dto.LabelRequestDto;
import com.ddalggak.finalproject.domain.label.service.LabelService;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;
import com.ddalggak.finalproject.global.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Label Controller", description = "Label 관련 API입니다.")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LabelController {
	private final LabelService labelService;

	@Operation(summary = "Label 생성", description = "api for creating label")
	@PostMapping("/label")
	public ResponseEntity<SuccessResponseDto> createLabel(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody LabelRequestDto labelRequestDto) {
		return labelService.createLabel(userDetails.getUser(), labelRequestDto);
	}

	@Operation(summary = "Label 삭제", description = "api for delete label")
	@DeleteMapping("/label/{labelId}")
	public ResponseEntity<SuccessResponseDto> deleteLabel(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody LabelRequestDto labelRequestDto,
		@PathVariable Long labelId) {
		return labelService.deleteLabel(userDetails.getUser(), labelRequestDto, labelId);
	}

	@Operation(summary = "Label 초대", description = "api for invite label")
	@PostMapping("/label/{labelId}/invite")
	public ResponseEntity<SuccessResponseDto> inviteLabel(
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody LabelRequestDto labelRequestDto,
		@PathVariable Long labelId) {
		return labelService.inviteLabel(userDetails.getUser(), labelRequestDto, labelId);
	}

	@Operation(summary = "label 리더 부여 및 해제", description = "api for assign/dismiss admin to label")
	@PostMapping("/label/{labelId}/leader")
	public ResponseEntity<SuccessResponseDto> manageLeader(
		@AuthenticationPrincipal UserDetailsImpl user,
		@PathVariable Long labelId,
		@RequestBody LabelRequestDto labelRequestDto) {
		return labelService.manageLeader(user.getUser(), labelRequestDto, labelId);
	}
}
