package com.ddalggak.finalproject.domain.ticket.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

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

	// @Builder
	// public TicketRequestDto(Ticket t) {
	// 	this.taskId = t.getTask().getTaskId();
	// 	this.ticketTitle = t.getTicketTitle();
	// 	this.ticketDescription = t.getTicketDescription();
	// 	this.totalPriority = t.getTotalPriority();
	// 	this.totalDifficulty = t.getTotalDifficulty();
	// 	this.assigned = t.getAssigned();
	// 	this.ticketExpiredAt = t.getTicketExpiredAt();
	// }
	//
	// public static TicketResponseDto of(Ticket ticket) {
	// 	return TicketResponseDto.builder()
	// 		.ticket(ticket)
	// 		.build();
	// }
}
