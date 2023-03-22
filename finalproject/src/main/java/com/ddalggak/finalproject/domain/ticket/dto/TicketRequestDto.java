package com.ddalggak.finalproject.domain.ticket.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequestDto {
	@Schema(name = "티켓 이름")
	private String ticketTitle;
	@Schema(name = "티켓 내용")
	private String ticketDescription;
	@Schema(name = "티켓 중요도")
	private Long priority;
	@Schema(name = "티켓 난이도")
	private Long difficulty;
	@Schema(name = "티켓 작성자")
	private String assigned;
	@Schema(name = "티켓 마감 날짜")
	private LocalDateTime expiredAt;
	@Schema(name = "댓글 작성")
	private String comment;
}
