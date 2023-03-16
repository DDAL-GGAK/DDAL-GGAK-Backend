package com.ddalggak.finalproject.domain.ticket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddalggak.finalproject.domain.ticket.dto.TicketResponseDto;
import com.ddalggak.finalproject.domain.ticket.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Tag(name = "스웨거 Ticket 테스트", description = "스웨거 Ticket 테스트 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket")
public class TicketController {
	private final TicketService ticketService;

	// 티켓 등록
	@Operation(summary = "post swagger", description = "Ticket 등록 post 메서드 체크")
	@PostMapping
	public ResponseEntity<TicketResponseDto> createTicket(
		@AuthenticationPrincipal UserDetailsImpl userDetails, // 변경필요
		@PathVariable("ticketId") Long ticket_id,	// 노란색 물결 member 다되면 다시 수정하기
		@RequestBody TicketResponseDto ticketResponseDto
	) {
		return ticketService.createTicket(userDetails, ticket_id, ticketResponseDto);
	}

	// 티켓 전체 조회 (테스크에 들어갈 내용) getTickets

	// 티켓 상세 조회
	@GetMapping("/{ticket_id}")
	public ResponseEntity<TicketResponseDto> getTicket(@PathVariable Long ticketId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ticketService.getTicket(ticketId, userDetails.getUser());
	}

	// 티켓 수정
	@PatchMapping("/{ticket_id}")
	public ResponseEntity<TicketResponseDto> updateTicket(@PathVariable Long ticketId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ticketService.updateTicket(ticketId, userDetails.getUser());
	}

	// 티켓 삭제
	@DeleteMapping("/{ticket_id}")
	public ResponseEntity<TicketResponseDto> deleteTicke(@PathVariable Long ticketId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ticketService.deleteTicke(ticketId, userDetails.getUser());
	}
}

