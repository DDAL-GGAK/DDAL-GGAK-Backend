package com.ddalggak.finalproject.domain.user.dto;

import com.ddalggak.finalproject.domain.project.entity.ProjectUser;
import com.ddalggak.finalproject.domain.task.entity.TaskUser;
import com.mysql.cj.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponseDto {

	public Long id;

	public String email;
	public String nickname;
	public String thumbnail;

	public String role;

	public UserResponseDto(ProjectUser projectUser) {
		this.id = projectUser.getUser().getUserId();
		this.email = projectUser.getUser().getEmail();
		this.nickname = projectUser.getUser().getNickname();
		this.thumbnail = projectUser.getUser().getProfile();
		this.role =
			projectUser.getProject().getProjectLeader().equals(projectUser.getUser().getEmail()) ? "LEADER" : "MEMBER";
	}

	public UserResponseDto(TaskUser taskUser) { //todo 이게 task단위로 늘어서 시간복잡도 많이 늘어날 수 있음
		this.id = taskUser.getUser().getUserId();
		this.email = taskUser.getUser().getEmail();
		this.nickname = taskUser.getUser().getNickname();
		this.thumbnail = taskUser.getUser().getProfile();
		this.role = StringUtils.isNullOrEmpty(taskUser.getTask().getTaskLeader()) ? "MEMBER" :
			taskUser.getTask().getTaskLeader().equals(taskUser.getUser().getEmail()) ? "LEADER" : "MEMBER";
	}

	public static UserResponseDto of(ProjectUser projectUser) {
		return new UserResponseDto(projectUser);
	}

	public static UserResponseDto of(TaskUser taskUser) {
		return new UserResponseDto(taskUser);
	}
}
