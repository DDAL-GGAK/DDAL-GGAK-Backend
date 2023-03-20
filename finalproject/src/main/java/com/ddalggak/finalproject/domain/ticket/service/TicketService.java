package com.ddalggak.finalproject.domain.ticket.service;

import static com.ddalggak.finalproject.global.error.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentResponseDto;
import com.ddalggak.finalproject.domain.ticket.comment.entity.Comment;
import com.ddalggak.finalproject.domain.ticket.comment.repository.CommentRepository;
import com.ddalggak.finalproject.domain.ticket.dto.TicketRequestDto;
import com.ddalggak.finalproject.domain.ticket.dto.TicketResponseDto;
import com.ddalggak.finalproject.domain.ticket.entity.Ticket;
import com.ddalggak.finalproject.domain.ticket.repository.TicketRepository;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.global.dto.SuccessCode;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;
import com.ddalggak.finalproject.global.error.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {
	private final TicketRepository ticketRepository;
	private final CommentRepository commentRepository;

	// 티켓 등록
	// @Transactional
	// public ResponseEntity<?> createTicket(User user, TicketRequestDto ticketRequestDto) {
	// 	System.out.println("--------user = " + user.getNickname());
	// 	ticketRepository.saveAndFlush(new Ticket(ticketRequestDto));
	// 	ResponseEntity.ok().body(TicketResponseDto.of(ticket));
	// 	return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	// 	// return ResponseEntity.createTicket(ticketResponseDto, userDetails.getUser());
	// }
	@Transactional
	public ResponseEntity<SuccessResponseDto> createTicket(TicketResponseDto ticketResponseDto,
		User user) {
		System.out.println("--------user = " + user.getNickname());
		Ticket ticket = ticketRepository.saveAndFlush(new Ticket(ticketResponseDto, user));
		ResponseEntity.ok().body(TicketResponseDto.of(ticket));
		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	}
	// public ResponseEntity<TicketResponseDto> createTicket(
	// 	UserDetailsImpl userDetails,
	// 	Long taskId,
	// 	TicketResponseDto ticketResponseDto) {
	// 	// 유저
	// 	User user = userDetails.getUser();
	// 	// 테스크
	// 	Task task = getTast(taskId);
	// 	// ticket 작성
	// 	Ticket ticket = new Ticket(user, task, ticketResponseDto);
	// 	ticketRepository.save(ticket);
	// 	// 상태 변환
	// 	return ResponseEntity.ok().body(TicketResponseDto.from(ticket.get()));
	// 	//TicketResponseDto.of(SuccessCode.CREATED_SUCCESSFULLY);
	// }
	//
	// // 티켓 전체조회 (테스크에 들어갈 내용)
	// // 티켓 상세조회 (해당되는 티켓만 조회 // 로직 다시 짜보기)
	// @Transactional(readOnly = true)
	// public List<TicketResponseDto> getTickets() {
	// 	List<TicketResponseDto> list = new ArrayList<>();
	// 	List<Ticket> ticketList;
	// 	ticketList = ticketRepository.findAll();
	// 	for (Ticket i : ticketList) {
	// 		list.add(new TicketResponseDto(i));
	// 	}
	// 	return list;
	// }
	//

	// 티켓 상세 조회
	@Transactional
	public ResponseEntity<SuccessResponseDto> getTicket(Long ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
			() ->  new CustomException(TICKET_NOT_FOUND));

		List<CommentResponseDto> commentList = getComment(ticket);
		TicketResponseDto ticketResponseDto = new TicketResponseDto(ticket, commentList);
		ResponseEntity.ok().body(ticketResponseDto);
		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	}
	// 티켓에 있는 댓글 가져오기
	private List<CommentResponseDto> getComment(Ticket ticket) {
		List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
		List<Comment> commentList = commentRepository.findAllByTicketOrderByCreatedAtDesc(ticket);
		for (Comment c : commentList) {
			CommentResponseDto commentDto = new CommentResponseDto(c);
		}
		return commentResponseDtoList;
	}


	// 티켓 수정하기 (티켓 수정이 가능했었나요? 일단 만들어 놓자!)
	@Transactional
	public ResponseEntity<SuccessResponseDto> updateTicket(Long ticketId,
		TicketRequestDto ticketRequestDto, User user) {
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
			() -> new CustomException(TICKET_NOT_FOUND)); // 권한에 접근할 수 없습니다 넣어도 될지 상의하기

		if (user.getUserId().equals(ticket.getUser().getNickname()))
			ticket.update(ticketRequestDto);
		else throw new CustomException(UNAUTHORIZED_USER);
		ResponseEntity.ok().body(TicketResponseDto.of(ticket));
		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);

	}

	// 티켓 삭제하기
	@Transactional
	public ResponseEntity<?> deleteTicket(Long ticketId, User user) {
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
			() -> new CustomException(TICKET_NOT_FOUND));
		if (user.getNickname().equals(ticket.getUser().getNickname()))
			ticketRepository.deleteById(ticketId);
		else throw new CustomException(UNAUTHORIZED_USER);
		ResponseEntity.ok().body("티켓 삭제 성공");
		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);

	}

	// 티켓 완료하기 티켓에 가져오기

}