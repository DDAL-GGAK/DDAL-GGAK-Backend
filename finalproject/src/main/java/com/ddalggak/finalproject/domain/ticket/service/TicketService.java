package com.ddalggak.finalproject.domain.ticket.service;

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
import com.ddalggak.finalproject.global.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {
	private final TicketRepository ticketRepository;

	// 티켓 등록
	@Transactional
	public ResponseEntity<TicketResponseDto> createTicket(
		UserDetailsImpl userDetails, Long taskId, TicketResponseDto ticketResponseDto
	) {
		// 유저
		User user = userDetails.getUser();
		// 테스크
		Task task = getTask(taskId)
		// ticket 작성
		Ticket ticket = new Ticket(user,task, ticketResponseDto);
		ticketRepository.save(ticket);
		// 상태 변환
		return TicketResponseDto.of(SuccessCode.CREATED_SUCCESSFULLY);
	}

	// 티켓 전체조회 (테스크에 들어갈 내용)

	// 티켓 상세조회
	@Transactional(readOnly = true)
	public List<TicketResponseDto> getTickets() {
		List<TicketResponseDto> list = new ArrayList<>();
		List<Ticket> ticketList;
		ticketList = ticketRepository.findAll();
		for (Ticket i : ticketList) {
			list.add(new TicketResponseDto(i));
		}
		return list;
	}
	// 티켓 1개 조회
	@Transactional
	public ResponseEntity<TicketResponseDto> getTask(Long ticketId, User user){
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
			() -> new IllegalArgumentException("티켓이 존재하지 않습니다.")
		);
		TicketResponseDto ticketResponseDto = new TicketResponseDto(ticket);
	}
}