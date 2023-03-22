package com.ddalggak.finalproject.domain.ticket.comment.service;

import static com.ddalggak.finalproject.global.error.ErrorCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddalggak.finalproject.domain.ticket.entity.Ticket;
import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentResponseDto;
import com.ddalggak.finalproject.domain.ticket.comment.entity.Comment;
import com.ddalggak.finalproject.domain.ticket.comment.repository.CommentRepository;
import com.ddalggak.finalproject.domain.ticket.repository.TicketRepository;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.global.dto.SuccessCode;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;
import com.ddalggak.finalproject.global.error.CustomException;
import com.ddalggak.finalproject.global.error.ErrorCode;
import com.ddalggak.finalproject.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final TicketRepository ticketRepository;

	// comment 등록
	// @Transactional
	// public BaseResponseDto createComment(CommentResponseDto commentResponseDto, UserDetailsImpl userDetails) {
	// 	User user = userDetails.getUser();
	// 	Ticket ticket = getComment(ticketId);
	// 	System.out.println("--------user = " + user.getNickname());
	// 	Comment comment = new Comment(user, ticket, commentResponseDto);
	// 	Comment comment = commentRepository.saveAndFlush(new Comment(commentResponseDto, user));
	// 	ResponseEntity.ok().body(LogResponseDto.of(log));
	// 	return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	// }
	@Transactional
	public ResponseEntity<SuccessResponseDto> createComment(UserDetailsImpl userDetails, Long ticketId,
		CommentResponseDto commentResponseDto) {
		User user = userDetails.getUser();
		Ticket ticket = getTicket(ticketId);
		// if (ticket) {
		// 	throw new CustomException(TICKET_NOT_FOUND);
		// }
		// comment 작성
		Comment comment =  new Comment(user, ticket, commentResponseDto);
		commentRepository.save(comment);
		// 상태 반환
		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	}
	// comment 전체 조회
	// @Transactional
	// public ResponseEntity<CommentResponseDto> getComments(User user, Long logId) {
	// 	Comment log = commentRepository.findById(logId).orElseThrow(
	// 		() -> new CustomException(LOG_NOT_FOUND));
	// 	CommentResponseDto logResponseDto = new CommentResponseDto(log);
	// 	return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	// }
	// comment 수정하기
	@Transactional
	public ResponseEntity<SuccessResponseDto> updateComment(Long ticketId,
		CommentResponseDto commentResponseDto, UserDetailsImpl userDetails) {
		User user = userDetails.getUser();
		Comment comment = commentRepository.findById(ticketId).orElseThrow(
			() -> new CustomException(COMMENT_NOT_FOUND));

		if (user.getEmail().equals(comment.getUser().getEmail()))
			comment.update(commentResponseDto.getComment());
		else throw new CustomException(UNAUTHORIZED_USER);
		// return ResponseEntity.ok().body(TicketResponseDto.of(ticket));
		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);

	}
	// comment 삭제하기
	// @Transactional
	// public ResponseEntity<?> deleteLog(Long logId, User user) {
	// 	Comment log = logRepository.findById(logId).orElseThrow(
	// 		() -> new CustomException(LOG_NOT_FOUND));
	// 	if (user.getNickname().equals(log.getOwner().getNickname()))
	// 		logRepository.deleteById(logId);
	// 	else throw new CustomException(UNAUTHORIZED_USER);
	// 	// return ResponseEntity.ok().body("티켓 삭제 성공");
	// 	return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	// }
	@Transactional
	public ResponseEntity<SuccessResponseDto> deleteComment(UserDetailsImpl userDetails, Long ticketId, Long commentId) {
		Ticket ticket = getTicket(ticketId);
		Comment comment = getComment(commentId);
		checkValidation(ticket, comment, userDetails);
		// 삭제
		commentRepository.delete(comment);
		// 상태 반환
		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	}

	/* == 반복 로직 == */

	// ticket 유무 확인
	private Ticket getTicket(Long ticketId) {
		return ticketRepository.findById(ticketId).orElseThrow(() -> new CustomException(TICKET_NOT_FOUND));
	}

	// comment 유무 확인
	private Comment getComment(Long commentId) {
		return commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));
	}

	// comment 유효성 검사
	private void checkValidation(Ticket ticket, Comment comment, UserDetailsImpl userDetails) {
		// ticket에 해당 comment가 있는지 검사
		if(!comment.getTicket().getTicketId().equals(ticket.getTicketId()))
			throw new CustomException(NOT_FOUND_COMMENT);
		// comment 작성자와 요청자의 일치 여부 검사
		if(!comment.getUser().getUserId().equals(userDetails.getUser().getUserId()))
			throw new CustomException(UNAUTHORIZED_MEMBER);
	}
}
