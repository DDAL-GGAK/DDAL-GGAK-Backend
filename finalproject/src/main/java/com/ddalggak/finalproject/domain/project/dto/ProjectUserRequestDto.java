package com.ddalggak.finalproject.domain.project.dto;

import com.ddalggak.finalproject.domain.project.entity.Project;
import com.ddalggak.finalproject.domain.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProjectUserRequestDto {

	@Schema(name = "project")
	public Project project;

	@Schema(name = "user", defaultValue = "anonymous")
	public User user;

	public static ProjectUserRequestDto create(User user) {
		return ProjectUserRequestDto.builder()
			.user(user)
			.build();
	}

	public static ProjectUserRequestDto join(Project project, User user) {
		return ProjectUserRequestDto.builder()
			.project(project)
			.user(user)
			.build();
	}
}
