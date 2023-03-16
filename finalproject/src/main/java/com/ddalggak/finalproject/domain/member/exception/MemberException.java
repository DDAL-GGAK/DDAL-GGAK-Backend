package com.ddalggak.finalproject.domain.member.exception;

import com.ddalggak.finalproject.global.error.ErrorCode;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

	private final ErrorCode errorCode;
	private final String message;

	public MemberException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.message = errorCode.getMessage();
	}
}