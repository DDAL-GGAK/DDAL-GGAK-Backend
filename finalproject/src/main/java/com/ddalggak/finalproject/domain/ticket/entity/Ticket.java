package com.ddalggak.finalproject.domain.ticket.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ddalggak.finalproject.domain.task.entity.Task;
import com.ddalggak.finalproject.domain.ticket.dto.TicketRequestDto;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.global.entity.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity

public class Ticket extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ticketId;

	// 티켓 제목 notnull
	@Column(nullable = false)
	private String ticketTitle;
	// 티켓 내용 notnull
	@Column(nullable = true)
	private String ticketDescription;
	// 중요도 null 허용 -> int 로 변경 필요
	@Column(nullable = true)
	private double priority;
	// 난이도  null 허용 -> int 로 변경 필요
	@Column(nullable = true)
	private double difficulty;
	// 태그(이름 변경 해야함)  null 허용
	@Column(nullable = true)
	private String assigned;
	// 마감 날짜  null 허용 -> 최신 생성순으로
	@Column(nullable = true)
	private LocalDateTime expiredAt;
	@Setter
	private String taskLeader;
	@Setter
	private String teamLeader;

	// task 연관관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taskId")
	private Task task;

	// user 연관관계 // FE에서 user -> onwer 로 변경요청
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "userId")
	// private User userList;

	@Column(nullable = true)
	@Enumerated(value = EnumType.STRING)
	private TicketStatus status;
	// @OneToMany(mappedBy = "ticket")
	// private List<User> User = new ArrayList<>();

	//label 연관관계 //티켓에 라벨이 필요한가? // 단방향으로 연관관계? 양방향?
	// @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
	// private List<Comment> comment = new ArrayList<>();

	@Builder
	public Ticket(TicketRequestDto ticketRequestDto, User user) {
		this.ticketTitle = ticketRequestDto.getTicketTitle();
		this.ticketDescription = ticketRequestDto.getTicketDescription();
		this.priority = ticketRequestDto.getPriority();
		this.difficulty = ticketRequestDto.getDifficulty();
		this.assigned = ticketRequestDto.getAssigned();
		// this.expiredAt = ticketRequestDto.getExpiredAt();
		this.taskLeader = user.getEmail();
		// this.addTask(task);
		this.status = ticketRequestDto.getStatus();
		this.teamLeader = user.getEmail();
		// this.comment = ticketResponseDto.getComments();
	}
	public void update(TicketRequestDto ticketRequestDto, User user) {
		this.ticketTitle = ticketRequestDto.getTicketTitle();
		this.ticketDescription = ticketRequestDto.getTicketDescription();
		this.priority = ticketRequestDto.getPriority();
		this.difficulty = ticketRequestDto.getDifficulty();
		this.assigned = ticketRequestDto.getAssigned();
		// this.expiredAt = ticketRequestDto.getExpiredAt();
		// this.addTask(task);
		this.status = ticketRequestDto.getStatus();
		this.taskLeader = user.getEmail();
		this.teamLeader = user.getEmail();
	}
	@Builder
	public static Ticket create(TicketRequestDto ticketRequestDto, User user, Task task) {
		return Ticket.builder()
			.ticketRequestDto(ticketRequestDto)
			.user(user)
			// .task(task)
			.build();
	}
	// private void addTicketUser(TaskUser taskUser) {
	// 	taskUser.addTask(taskUser.getTask());
	// 	task.addTicket(this);
	// }
	// private void addTask(Task task) {
	// 	this.task = task;
	// 	task.addTicket(this);
	// }
}
