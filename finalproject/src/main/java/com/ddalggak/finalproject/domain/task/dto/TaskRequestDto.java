package com.ddalggak.finalproject.domain.task.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class TaskRequestDto {

	@Schema(name = "project Id", example = "project Id")
	@NotNull(message = "project Id is required")
	public Long projectId;

	@Schema(name = "task title", example = "task title")
	public String taskTitle;

	@Schema(name = "when does this project expired at", example = "2023-03-22")
	public LocalDate expiredAt;

	@Schema(name = "email for invite or something to do with user", example = "kimdaehyun@daum.net")
	public String email;
}
