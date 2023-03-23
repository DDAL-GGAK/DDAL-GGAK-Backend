package com.ddalggak.finalproject.domain.task.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class TaskRequestDto {
	@Schema(name = "task title", example = "task title")
	public String taskTitle;

	@Schema(name = "when does this project expired at", example = "2023-03-22")
	public LocalDate expiredAt;
}
