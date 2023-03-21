package com.ddalggak.finalproject.domain.task.dto;

import java.time.LocalDate;

import com.ddalggak.finalproject.domain.task.entity.Task;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskBriefResponseDto { // task 간단 요약제공

	public String taskTitle;

	public LocalDate expiredAt;

	@Builder
	public TaskBriefResponseDto(Task task) {
		taskTitle = task.getTaskTitle();
		expiredAt = task.getExpiredAt();
	}

}
