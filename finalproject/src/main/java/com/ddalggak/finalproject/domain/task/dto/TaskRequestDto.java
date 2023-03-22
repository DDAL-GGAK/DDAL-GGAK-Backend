package com.ddalggak.finalproject.domain.task.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class TaskRequestDto {

	public String taskTitle;

	public LocalDate expiredAt;
}
