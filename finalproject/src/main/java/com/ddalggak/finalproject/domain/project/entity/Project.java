package com.ddalggak.finalproject.domain.project.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ddalggak.finalproject.domain.project.dto.ProjectRequestDto;
import com.ddalggak.finalproject.domain.task.entity.Task;
import com.ddalggak.finalproject.global.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectId;

	private String projectTitle;

	private String thumbnail;

	private LocalDateTime expiredAt;

	@OneToMany(mappedBy = "project")
	private List<ProjectUser> projectUserList = new ArrayList<>();

	@OneToMany(mappedBy = "project")
	private List<Task> taskList = new ArrayList<>();

	public Project(ProjectRequestDto projectRequestDto) {
		projectTitle = projectRequestDto.getTitle();
		thumbnail = projectRequestDto.getThumbnail();
		expiredAt = projectRequestDto.getExpiredAt();
	}
}
