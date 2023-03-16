package com.ddalggak.finalproject.domain.ticket.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddalggak.finalproject.domain.ticket.dto.TicketResponseDto;
import com.ddalggak.finalproject.domain.ticket.entity.Ticket;
import com.ddalggak.finalproject.domain.ticket.repository.TicketRepository;
import com.ddalggak.finalproject.global.dto.SuccessCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {
	private final TicketRepository ticketRepository;

	// 티켓 등록
	public ResponseEntity<TicketResponseDto> createTicket(
		UserDetailsImpl userDetails, Long ticket_id, TicketResponseDto ticketResponseDto
	) {
		// 유저
		// 테스크

		// ticket 작성
		Ticket ticket = new Ticket();
		ticketRepository.save(ticket);
		// 상태 변환
		return TicketResponseDto.of(SuccessCode.CREATED_SUCCESSFULLY);
	}
}