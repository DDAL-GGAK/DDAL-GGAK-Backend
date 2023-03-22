package com.ddalggak.finalproject.domain.project.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ddalggak.finalproject.domain.project.dto.ProjectUserRequestDto;
import com.ddalggak.finalproject.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Project_User")
@NoArgsConstructor
public class ProjectUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ProjectId")
	private Project project;

	@Builder
	public ProjectUser(User user) {
		this.user = user;
	}

	public static ProjectUser create(ProjectUserRequestDto projectUserDto) {
		return ProjectUser.builder()
			.user(projectUserDto.getUser())
			.build();
	}

	public void addProject(Project project) {
		this.project = project;
	}
}
