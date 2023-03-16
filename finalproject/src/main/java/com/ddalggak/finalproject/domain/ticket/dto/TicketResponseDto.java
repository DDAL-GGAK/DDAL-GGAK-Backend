package com.ddalggak.finalproject.domain.ticket.dto;

import java.time.LocalDateTime;

import com.ddalggak.finalproject.domain.ticket.entity.Ticket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketResponseDto {
	private Long ticketId;
	private String ticketTitle;
	private String ticketDescription;
	private double priority;
	private double difficulty;
	private String assigned;
	private LocalDateTime expiredAt;

	@Builder
	public TicketResponseDto(Ticket t) {
		this.ticketId = t.getTicketId();
		this.ticketTitle = t.getTicketTitle();
		this.ticketDescription = t.getTicketDescription();
		this.priority = t.getPriority();
		this.difficulty = t.getDifficulty();
		this.assigned = t.getAssigned();
		this.expiredAt = t.getExpiredAt();
	}

	public static TicketResponseDto of(Ticket ticket) {
		return TicketResponseDto.builder()
			.t(ticket)
			.build();
	}
}
