package com.ddalggak.finalproject.domain.ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddalggak.finalproject.domain.ticket.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	// 테스크 티켓 조회 (테스크로 수정 필요)
	List<Ticket> findAllBytaskOrderBy_cratedAtDesc(Task task);

	// 티켓 상세 조회
	List<Ticket> findAllByOrderBymodifiedAtDesc();
}
