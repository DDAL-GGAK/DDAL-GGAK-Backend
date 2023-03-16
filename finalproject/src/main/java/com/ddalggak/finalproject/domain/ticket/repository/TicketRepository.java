package com.ddalggak.finalproject.domain.ticket.repository;

import java.util.List;

import com.ddalggak.finalproject.domain.ticket.entity.Ticket;

public interface TicketRepository {
	// 테스크 티켓 조회 (테스크로 수정 필요)
	List<Ticket> findAllByTicketOrderByCratedAtDesc(Ticket ticket);

	// 티켓 상세 조회
	List<Ticket> findAllByOrderByModifiedAtDesc();
}
