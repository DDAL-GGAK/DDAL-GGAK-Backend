package com.ddalggak.finalproject.domain.ticket.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;

import com.ddalggak.finalproject.domain.project.dto.ProjectResponseDto;
import com.ddalggak.finalproject.domain.project.entity.Project;
import com.ddalggak.finalproject.domain.task.dto.TaskBriefResponseDto;
import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentResponseDto;
import com.ddalggak.finalproject.domain.ticket.entity.Ticket;
import com.ddalggak.finalproject.domain.ticket.entity.TicketStatus;
import com.ddalggak.finalproject.domain.user.dto.UserResponseDto;

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
	private LocalDate ticketExpiredAt;
	private List<CommentResponseDto> comments;
	private TicketStatus status;

	@Builder
	public TicketResponseDto(Ticket ticket) {//, List<CommentResponseDto> comments) {
		ticketId = ticket.getTicketId();
		ticketTitle = ticket.getTicketTitle();
		ticketDescription = ticket.getTicketDescription();
		priority = (long)ticket.getPriority();
		difficulty = (long)ticket.getDifficulty();
		assigned = ticket.getAssigned();
		ticketExpiredAt = LocalDate.from(ticket.getExpiredAt());
		status = ticket.getStatus();
		// this.comments = comments;
	}

	public static TicketResponseDto of(Ticket ticket) {
		return TicketResponseDto.builder()
			.ticket(ticket)
			.build();
	}
	public static ResponseEntity<TicketResponseDto> ticketResponseDtoResponseEntity(Ticket ticket) {
		return ResponseEntity
			.status(200)
			.body(TicketResponseDto.builder()
				.ticket(ticket)
				.build());
	}
}
