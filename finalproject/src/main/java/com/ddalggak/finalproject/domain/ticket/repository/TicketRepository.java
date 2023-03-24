package com.ddalggak.finalproject.domain.ticket.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.ddalggak.finalproject.domain.ticket.dto.TicketRequestDto;
import com.ddalggak.finalproject.domain.ticket.entity.Ticket;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	// Ticket findAllBytaskIdByticketId( Long taskId, Long ticketId);

	// List<Ticket> findAllByTicketOrderByCratedAtDesc(Ticket ticket);

	// List<Ticket> findAllByOrderByModifiedAtDesc();
}
