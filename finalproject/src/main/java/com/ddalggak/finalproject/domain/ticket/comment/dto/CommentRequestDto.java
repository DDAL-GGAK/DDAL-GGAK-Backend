package com.ddalggak.finalproject.domain.ticket.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto{
	public CommentRequestDto(String comments) { this.comments = comments; }
	private String comments;
}
