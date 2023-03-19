package com.ddalggak.finalproject.domain.ticket.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.ddalggak.finalproject.domain.task.entity.TaskUser;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.global.entity.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class TicketLog extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long logId;
	@Column(nullable = false)
	private String logDescription; //logDescription 바꾸기
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticketId")
	private Ticket ticket;
	@OneToMany(mappedBy = "ticket")
	private List<User> owner = new ArrayList<>();

	public TicketLog(List<User> owner, Ticket ticket, String logDescription) {
		this.owner = owner;
		this.ticket = ticket;
		this.logDescription = logDescription;

	}

}
