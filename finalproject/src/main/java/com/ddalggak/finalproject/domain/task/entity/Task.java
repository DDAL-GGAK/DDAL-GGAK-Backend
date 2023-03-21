package com.ddalggak.finalproject.domain.task.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.BatchSize;

import com.ddalggak.finalproject.domain.project.entity.Project;
import com.ddalggak.finalproject.domain.task.dto.TaskRequestDto;
import com.ddalggak.finalproject.domain.user.entity.Label;
import com.ddalggak.finalproject.global.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long taskId;

	private String taskTitle;

	private int totalPriority;

	private int totalDifficulty;

	private LocalDate expiredAt;

	private String taskLeader;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

	@OneToMany(mappedBy = "task")
	private List<Label> labelList = new ArrayList<>();

	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	@BatchSize(size = 500)
	private List<TaskUser> taskUserList = new ArrayList<>();

	@Builder
	public Task(TaskRequestDto taskRequestDto, TaskUser taskUser, Project project) {
		this.taskTitle = taskRequestDto.getTaskTitle();
		this.expiredAt = taskRequestDto.getExpiredAt();
		this.taskLeader = taskUser.getUser().getEmail();
		this.addProject(project);
		this.addTaskUser(taskUser);
	}

	public static Task create(TaskRequestDto taskRequestDto, TaskUser taskUser, Project project) {
		return Task.builder()
			.taskRequestDto(taskRequestDto)
			.taskUser(taskUser)
			.project(project)
			.build();
	}

	//연관관계 편의 메소드

	private void addTaskUser(TaskUser taskUser) {
		this.taskUserList.add(taskUser);
		taskUser.addTask(this);
	}

	private void addProject(Project project) {
		this.project = project;
		project.addTask(this);
	}
}
