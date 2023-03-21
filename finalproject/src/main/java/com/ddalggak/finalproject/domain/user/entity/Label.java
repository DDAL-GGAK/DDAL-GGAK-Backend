package com.ddalggak.finalproject.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.ddalggak.finalproject.domain.task.entity.Task;
import com.ddalggak.finalproject.domain.ticket.entity.Ticket;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long labelId;

	private String labelName;

	private String labelLeader;
	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;

	@OneToMany(mappedBy = "label")
	private List<User> userList = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "ticket")
	private Ticket ticket;

}
