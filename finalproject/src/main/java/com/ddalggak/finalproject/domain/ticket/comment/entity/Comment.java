package com.ddalggak.finalproject.domain.ticket.comment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentResponseDto;
import com.ddalggak.finalproject.domain.ticket.entity.Ticket;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.global.entity.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;
	@Column(nullable = false)
	private String comments; //logDescription 바꾸기
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticketId")
	private Ticket ticket;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

	public Comment(User user, Ticket ticket, CommentResponseDto comments) {
		// this.owner = this.getOwner();
		this.user = user;
		this.ticket = ticket;
		this.comments = String.valueOf(comments);

	}

	public void update(CommentResponseDto commentResponseDto) {
	}
}
