package com.ddalggak.finalproject.global.jwt.token.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenResponseDto {
	private String accessToken;

	private String refreshToken;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:MM:ss", timezone = "Asia/Seoul")
	private Date accessTokenExpireTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:MM:ss", timezone = "Asia/Seoul")
	private Date refreshTokenExpireTime;
}
