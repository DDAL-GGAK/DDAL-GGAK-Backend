package com.ddalggak.finalproject.domain.ticket.comment.dto;

import java.time.LocalDateTime;

import com.ddalggak.finalproject.domain.ticket.comment.entity.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto{
	private Long commentId;
	private String comment;
	// private String description;
	private String email;
	private LocalDateTime createdAt;
	// private LocalDateTime modifiedAt;
	// private User user;

	@Builder
	public CommentRequestDto(Comment c) {
		this.commentId = c.getCommentId();
		this.comment = c.getComment();
		// this.description = getDescription();
		this.email = c.getUser().getEmail();
		this.createdAt = c.getCreatedAt();
		// this.modifiedAt = c.getModifiedAt();
		// this.user = c.getUser();
	}
}