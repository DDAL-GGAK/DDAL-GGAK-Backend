package com.ddalggak.finalproject.domain.ticket.entity;

public enum TicketStatus {
	TODO("할 일"),
	IN_PROGRESS("진행중"),
	DONE("완료");

	private final String value;

	private TicketStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
