package com.ddalggak.finalproject.domain.ticket.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.ddalggak.finalproject.domain.task.entity.Task;
import com.ddalggak.finalproject.domain.ticket.comment.entity.Comment;
import com.ddalggak.finalproject.domain.ticket.dto.TicketRequestDto;
import com.ddalggak.finalproject.domain.ticket.dto.TicketResponseDto;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.global.entity.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
	// task 연관관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "taskId")
	private Task task;
	// user 연관관계 // FE에서 user -> onwer 로 변경요청
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;
	// @OneToMany(mappedBy = "ticket")
	// private List<User> User = new ArrayList<>();

	// label 연관관계 //티켓에 라벨이 필요한가? // 단방향으로 연관관계? 양방향?
	@OneToMany(mappedBy = "Comment", cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();

	public Ticket(TicketResponseDto ticketResponseDto, User user) {
		this.ticketTitle = ticketResponseDto.getTicketTitle();
		this.ticketDescription = ticketResponseDto.getTicketDescription();
		this.priority = ticketResponseDto.getPriority();
		this.difficulty = ticketResponseDto.getDifficulty();
		this.assigned = ticketResponseDto.getAssigned();
		this.expiredAt = ticketResponseDto.getExpiredAt();
		this.user = user;
	}
	public void update(TicketRequestDto ticketRequestDto) {
		this.ticketTitle = ticketRequestDto.getTicketTitle();
		this.ticketDescription = ticketRequestDto.getTicketDescription();
		// this.user = user;
	}
}
