package com.ddalggak.finalproject.domain.task.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ddalggak.finalproject.domain.task.dto.TaskUserRequestDto;
import com.ddalggak.finalproject.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Task_User")
@NoArgsConstructor
public class TaskUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;

	@ManyToOne
	@JoinColumn(name = "TaskId")
	private Task task;

	@Builder
	public TaskUser(User user) {
		this.user = user;
	}

	public static TaskUser create(TaskUserRequestDto taskUserRequestDto) {
		return TaskUser.builder()
			.user(taskUserRequestDto.getUser())
			.build();
	}

	public void addTask(Task task) {
		this.task = task;
	}
}
