package com.ddalggak.finalproject.domain.ticket.dto;

import java.time.LocalDate;
import java.util.List;

import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentResponseDto;
import com.ddalggak.finalproject.domain.ticket.entity.Ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TicketResponseDto {
	@Schema(name = "ticket id", example = "1")
	private Long ticketId;
	@Schema(name = "ticket title", example = "ticket title")
	private String ticketTitle;
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
	private List<CommentResponseDto> commentList;
	@Schema(name = "label leader", example = "label leader")
	private String labelLeader;

	@Builder
	public TicketResponseDto(Ticket ticket, List<CommentResponseDto> commentList) {
		this.ticketId = ticket.getTicketId();
		this.ticketTitle = ticket.getTicketTitle();
		this.ticketDescription = ticket.getTicketDescription();
		this.totalPriority = ticket.getTotalPriority();
		this.totalDifficulty = ticket.getTotalDifficulty();
		this.assigned = ticket.getAssigned();
		this.ticketExpiredAt = ticket.getTicketExpiredAt();
		this.commentList = commentList;
	}

	public static TicketResponseDto of(Ticket ticket) {
		return TicketResponseDto.builder()
			.ticket(ticket)
			.build();
	}


	// public static ResponseEntity<TicketResponseDto>ticketResponseDtoResponseEntity of(Ticket ticket) {
	// 	return ResponseEntity
	// 		.status(200)
	// 		.body(TicketResponseDto.builder()
	// 			.ticket(ticket)
	// 			.build());
	// }


}
