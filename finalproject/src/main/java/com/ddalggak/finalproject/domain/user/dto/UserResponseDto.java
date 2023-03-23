package com.ddalggak.finalproject.domain.user.dto;

import com.ddalggak.finalproject.domain.project.entity.ProjectUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
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

	@Builder
	public UserResponseDto(ProjectUser projectUser) {
		this.id = projectUser.getUser().getUserId();
		this.email = projectUser.getUser().getEmail();
		this.nickname = projectUser.getUser().getNickname();
		// this.thumbnail = projectUser.getUser().getThumbnail();
	}

	public static UserResponseDto of(ProjectUser projectUser) {
		return UserResponseDto.builder()
			.projectUser(projectUser)
			.build();
	}
}
