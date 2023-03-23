package com.ddalggak.finalproject.domain.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProjectRequestDto {

	@Schema(name = "프로젝트 이름")
	public String projectTitle;

	@Schema(name = "프로젝트 썸네일", example = "http://ddalggak.ap-northeast-1.amazonaws.com/thumbnail/projects/~")
	public String thumbnail;

}
