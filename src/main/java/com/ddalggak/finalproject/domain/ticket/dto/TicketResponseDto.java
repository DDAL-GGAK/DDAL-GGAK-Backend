package com.ddalggak.finalproject.domain.ticket.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentResponseDto;
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
	private Long priority;
	private Long difficulty;
	private String assigned;
	private LocalDateTime expiredAt;
	private List<CommentResponseDto> comments;

	@Builder
	public TicketResponseDto(Ticket t, List<CommentResponseDto> comments) {
		this.ticketId = t.getTicketId();
		this.ticketTitle = t.getTicketTitle();
		this.ticketDescription = t.getTicketDescription();
		this.priority = (long)t.getPriority();
		this.difficulty = (long)t.getDifficulty();
		this.assigned = t.getAssigned();
		this.expiredAt = t.getExpiredAt();
		this.comments = comments;
	}

	public static TicketResponseDto of(Ticket ticket) {
		return TicketResponseDto.builder()
			.t(ticket)
			.build();
	}
}
