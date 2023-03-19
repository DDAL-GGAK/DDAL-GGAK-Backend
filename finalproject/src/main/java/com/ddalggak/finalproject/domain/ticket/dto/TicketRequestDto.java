package com.ddalggak.finalproject.domain.ticket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequestDto {
	private String ticketTitle;
	private String ticketDescription;
	private double priority;
	private double difficulty;

}
