package com.ddalggak.finalproject.domain.ticket.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentResponseDto;
import com.ddalggak.finalproject.domain.ticket.entity.Ticket;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketRequestDto {
	@Schema(name = "task Id")
	@NotNull(message = "task Id is required")
	private Long taskId;
	@Schema(name = "ticket title", example = "ticket title")
	private String ticketTitle;
	@Schema(name = "ticket Description", example = "ticket Description")
	private String ticketDescription;
	@Schema(name = "ticket priority", example = "ticket priority")
	private int totalPriority;
	@Schema(name = "ticket difficulty", example = "ticket difficulty")
	private int totalDifficulty;
	@Schema(name = "ticket assigned", example = "ticket assigned")
	private String assigned;
	@Schema(name = "when does this project expired at", example = "2023-03-22")
	private LocalDate ticketExpiredAt;

	@Builder
	public TicketRequestDto(Ticket ticket, List<CommentResponseDto> comments) {
		Long ticketId = ticket.getTicketId();
		ticketTitle = ticket.getTicketTitle();
		ticketDescription = ticket.getTicketDescription();
		totalPriority = ticket.getTotalPriority();
		totalDifficulty = ticket.getTotalDifficulty();
		assigned = ticket.getAssigned();
		ticketExpiredAt = ticket.getTicketExpiredAt();
		// comments = ticket.getComment().stream().map(CommentResponseDto::of).collect(Collectors.toCollection());

	}
	// public static TicketResponseDto of(Ticket ticket) {
	// 	return TicketResponseDto.builder()
	// 		.ticket(ticket)
	// 		.build();
	// }
}
