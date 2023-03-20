package com.ddalggak.finalproject.domain.ticket.comment.dto;

import com.ddalggak.finalproject.domain.ticket.comment.entity.Comment;
import com.ddalggak.finalproject.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
	private Long commentId;
	private String description;
	private User user;

	@Builder
	public CommentResponseDto(Comment c) {
		this.commentId = c.getCommentId();
		this.description = getDescription();
		this.user = c.getUser();
	}
}
