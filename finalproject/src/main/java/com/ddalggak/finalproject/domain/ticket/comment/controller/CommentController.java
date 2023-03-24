package com.ddalggak.finalproject.domain.ticket.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentRequestDto;
import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentResponseDto;
import com.ddalggak.finalproject.domain.ticket.comment.service.CommentService;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;
import com.ddalggak.finalproject.global.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Log Controller", description = "로그 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{ticketId}/comment")
public class CommentController {
	private final CommentService commentService;

	// 댓글 등록
	@Operation(summary = "ticket comment", description = "comment 등록 post 메서드 체크")
	@PostMapping("")
	public ResponseEntity<?> createComment
	(	@PathVariable Long ticketId,
		@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody CommentRequestDto commentRequestDto
	) {
		return commentService.createComment(userDetails, ticketId, commentId, commentRequestDto);
	}

	// // 로그 전체 조회
	// @Operation(summary = "get ticket log", description = "comment 전체 조회 get 메서드 체크")
	// @GetMapping("/comment")
	// public ResponseEntity<BaseResponseDto> getLogs(
	// 	@PathVariable Long logId,
	// 	@AuthenticationPrincipal UserDetailsImpl userDetails) {
	// 	return commentService.getComments(userDetails.getUser(), logId);
	// }

	// 댓글 수정
	@Operation(summary = "patch ticket comment", description = "comment 수정 get 메서드 체크")
	@PatchMapping("/{commentId}")
	public ResponseEntity<SuccessResponseDto> updateComment(
		@PathVariable Long ticketId,
		@PathVariable Long commentId,
		@RequestBody CommentRequestDto commentRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.updateComment(ticketId, commentId, commentRequestDto, userDetails);
	}
	// 댓글 삭제
	@Operation(summary = "delete ticket comment", description = "comment 삭제 delete 메서드 체크")
	@DeleteMapping("/{commentId}")
	public ResponseEntity<SuccessResponseDto> deleteComment(
		@PathVariable Long ticketId,
		@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.deleteComment(userDetails, ticketId, commentId);
	}
}
