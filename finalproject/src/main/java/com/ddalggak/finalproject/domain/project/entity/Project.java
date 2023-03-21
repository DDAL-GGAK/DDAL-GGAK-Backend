package com.ddalggak.finalproject.domain.project.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.BatchSize;

import com.ddalggak.finalproject.domain.project.dto.ProjectRequestDto;
import com.ddalggak.finalproject.domain.task.entity.Task;
import com.ddalggak.finalproject.global.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectId;

	@Column(nullable = false)
	private String projectTitle;

	private String thumbnail;

	@Column(nullable = false)
	private LocalDate expiredAt;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	@BatchSize(size = 500)
	private List<ProjectUser> projectUserList = new ArrayList<>();

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	@BatchSize(size = 500)
	private List<Task> taskList = new ArrayList<>();

	@Builder
	public Project(ProjectRequestDto projectRequestDto, ProjectUser projectUser) {
		this.projectTitle = projectRequestDto.getProjectTitle();
		this.thumbnail = projectRequestDto.getThumbnail();
		this.expiredAt = projectRequestDto.getExpiredAt();
		this.addProjectUser(projectUser);
	}

	public static Project create(ProjectRequestDto projectRequestDto, ProjectUser projectUser) {
		return Project.builder()
			.projectRequestDto(projectRequestDto)
			.projectUser(projectUser)
			.build();
	}

	//연관관계 편의 메소드
	public void addProjectUser(ProjectUser projectUser) {
		projectUserList.add(projectUser);
		projectUser.addProject(this);
	}

	public void addTask(Task task) {
		taskList.add(task);
	}
}