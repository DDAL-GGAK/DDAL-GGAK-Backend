package com.ddalggak.finalproject.domain.ticket.service;

import static com.ddalggak.finalproject.global.error.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddalggak.finalproject.domain.ticket.dto.TicketResponseDto;
import com.ddalggak.finalproject.domain.ticket.entity.Ticket;
import com.ddalggak.finalproject.domain.ticket.repository.TicketRepository;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.global.dto.SuccessCode;
import com.ddalggak.finalproject.global.error.CustomException;
import com.ddalggak.finalproject.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {
	private final TicketRepository ticketRepository;

	// 티켓 등록 //Task에 넣어야 하기 때문에 Task가 필요하다~~
	@Transactional
	public ResponseEntity<TicketResponseDto> createTicket(
		UserDetailsImpl userDetails,
		Long taskId,
		TicketResponseDto ticketResponseDto) {
		// 유저
		User user = userDetails.getUser();
		// 테스크
		Task task = getTask(taskId);
		// ticket 작성
		Ticket ticket = new Ticket(user, task, ticketResponseDto);
		ticketRepository.save(ticket);
		// 상태 변환
		return ResponseEntity.ok().body(TicketResponseDto.from(ticket.get()));
		//TicketResponseDto.of(SuccessCode.CREATED_SUCCESSFULLY);
	}

	// 티켓 전체조회 (테스크에 들어갈 내용)
	// 티켓 상세조회 (해당되는 티켓만 조회 // 로직 다시 짜보기)
	// @Transactional(readOnly = true)
	// public List<TicketResponseDto> getTicket() {
	// 	List<TicketResponseDto> list = new ArrayList<>();
	// 	List<Ticket> ticketList;
	// 	ticketList = ticketRepository.findAll();
	// 	for (Ticket i : ticketList) {
	// 		list.add(new TicketResponseDto(i));
	// 	}
	// 	return list;
	// }

	// 티켓 1개 조회
	@Transactional
	public ResponseEntity<TicketResponseDto> getTicket(Long ticketId, User user){
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
			() -> new CustomException(NOT_FOUND_TICKET));
		TicketResponseDto ticketResponseDto = new TicketResponseDto(ticket);
		return ResponseEntity.ok().body(ticketResponseDto);
	}

	// 티켓 수정하기 (티켓 수정이 가능했었나요? 일단 만들어 놓자!)
	@Transactional
	public ResponseEntity<TicketResponseDto> updateTicket(Long ticketId, TicketResponseDto ticketResponseDto, User user) {
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
			() -> new CustomException(NOT_FOUND_TICKET)); // 권한에 접근할 수 없습니다 넣어도 될지 상의하기
		if (user.getNickname().equals(ticket.getUser().getNickname()))
			ticket.update(ticketResponseDto);
		else throw new CustomException(UNAUTHORIZED_USER);
		return ResponseEntity.ok().body(TicketResponseDto.of(ticket));
	}

	// 티켓 삭제하기
	@Transactional
	public ResponseEntity deleteTicket(Long ticketId, User user) {
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
			() -> new CustomException(NOT_FOUND_TICKET));
		if (user.getNickname().equals(ticket.getUser().getNickname()))
			ticketRepository.deleteById(ticketId);
		else throw new CustomException(UNAUTHORIZED_USER);
		return ResponseEntity.ok().body("티켓 삭제 성공");
	}

	// 티켓 완료하기 티켓에 가져오기

}