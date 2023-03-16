package com.ddalggak.finalproject.domain.ticket.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ticketId;

	// 티켓 제목 notnull
	@Column(nullable = false)
	private String ticketTitle;
	// 티켓 내용 notnull
	@Column(nullable = true)
	private String ticketDescription;
	// 중요도 null 허용
	@Column(nullable = true)
	private double priority;
	// 난이도  null 허용
	@Column(nullable = true)
	private double difficulty;
	// 태그(이름 변경 해야함)  null 허용
	@Column(nullable = true)
	private String assigned;
	// 마감 날짜  null 허용 -> 마감날짜를 필수로 넣어야하는가?
	@Column(nullable = true)
	private LocalDateTime expiredAt;

}
