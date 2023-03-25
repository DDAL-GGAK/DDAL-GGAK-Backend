package com.ddalggak.finalproject.domain.ticket.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;


import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentResponseDto;
import com.ddalggak.finalproject.domain.ticket.entity.Ticket;
import com.ddalggak.finalproject.domain.ticket.entity.TicketStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;



public class TicketResponseDto {
	@Schema(name = "ticket id", example = "1")
	private Long ticketId;
	@Schema(name = "ticket title", example = "ticket title")
	private String ticketTitle;
	@Schema(name = "label leader", example = "label leader")
	private String labelLeader;
	@Schema(name = "ticket description", example = "ticket description")
	private String ticketDescription;
	@Schema(name = "ticket priority", example = "ticket priority")
	private int totalPriority;
	@Schema(name = "ticket difficulty", example = "ticket difficulty")
	private int totalDifficulty;
	@Schema(name = "ticket assigned", example = "ticket assigned")
	private String assigned;
	@Schema(name = "ticket assigned", example = "ticket assigned")
	private LocalDate ticketExpiredAt;
	@Schema(name = "total comments")
	private List<CommentResponseDto> comments;


	@Builder
	public TicketResponseDto(Ticket ticket, List<CommentResponseDto> comments) {
		ticketId = ticket.getTicketId();
		ticketTitle = ticket.getTicketTitle();
		ticketDescription = ticket.getTicketDescription();
		totalPriority = ticket.getTotalPriority();
		totalDifficulty = ticket.getTotalDifficulty();
		assigned = ticket.getAssigned();
		ticketExpiredAt = ticket.getTicketExpiredAt();
		comments = comments;

	}

	public static TicketResponseDto of(Ticket ticket) {
		return TicketResponseDto.builder()
			.ticket(ticket)
			.build();
	}
	// public static ResponseEntity<TicketResponseDto> ticketResponseDtoResponseEntity(Ticket ticket) {
	// 	return ResponseEntity
	// 		.status(200)
	// 		.body(TicketResponseDto.builder()
	// 			.ticket(ticket)
	// 			.build());
	// }
}
