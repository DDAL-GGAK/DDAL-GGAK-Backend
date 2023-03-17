package com.ddalggak.finalproject.domain.project.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProjectRequestDto {

	@Schema(name = "프로젝트 이름")
	public String title;

	@Schema(name = "프로젝트 썸네일", example = "http://ddalggak.ap-northeast-1.amazonaws.com/thumbnail/projects/~")
	public String thumbnail;

	@Schema(name = "프로젝트 만료일", example = "2023-04-16 00:00:00")
	public LocalDateTime expiredAt;

}
