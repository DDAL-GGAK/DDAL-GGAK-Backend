package com.ddalggak.finalproject.domain.ticket.dto;

import java.time.LocalDate;
import java.util.List;
import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentResponseDto;
import com.ddalggak.finalproject.domain.ticket.entity.Ticket;
import com.ddalggak.finalproject.domain.ticket.entity.TicketStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketRequestDto {
	private Long ticketId;
	private String ticketTitle;
	private String ticketDescription;
	private Long priority;
	private Long difficulty;
	private String assigned;
	private LocalDate expiredAt;
	private TicketStatus status;


	@Builder
	public TicketRequestDto(Ticket t) {
		this.ticketId = t.getTicketId();
		this.ticketTitle = t.getTicketTitle();
		this.ticketDescription = t.getTicketDescription();
		this.priority = (long)t.getPriority();
		this.difficulty = (long)t.getDifficulty();
		this.assigned = t.getAssigned();
		this.status = t.getStatus();
		this.expiredAt = LocalDate.from(t.getExpiredAt());
	}

	public static TicketResponseDto of(Ticket ticket) {
		return TicketResponseDto.builder()
			.ticket(ticket)
			.build();
	}
}
