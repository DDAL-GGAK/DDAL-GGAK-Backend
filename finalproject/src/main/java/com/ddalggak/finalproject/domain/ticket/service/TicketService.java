package com.ddalggak.finalproject.domain.ticket.service;

import static com.ddalggak.finalproject.global.error.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ddalggak.finalproject.domain.label.entity.Label;
import com.ddalggak.finalproject.domain.task.dto.TaskUserRequestDto;
import com.ddalggak.finalproject.domain.task.entity.Task;
import com.ddalggak.finalproject.domain.task.entity.TaskUser;
import com.ddalggak.finalproject.domain.task.repository.TaskRepository;
import com.ddalggak.finalproject.domain.ticket.comment.dto.CommentResponseDto;
import com.ddalggak.finalproject.domain.ticket.comment.entity.Comment;
import com.ddalggak.finalproject.domain.ticket.comment.repository.CommentRepository;
import com.ddalggak.finalproject.domain.ticket.dto.TicketRequestDto;
import com.ddalggak.finalproject.domain.ticket.dto.TicketResponseDto;
import com.ddalggak.finalproject.domain.ticket.entity.Ticket;
import com.ddalggak.finalproject.domain.ticket.repository.TicketRepository;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.domain.user.repository.UserRepository;
import com.ddalggak.finalproject.global.dto.SuccessCode;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;
import com.ddalggak.finalproject.global.error.CustomException;
import com.ddalggak.finalproject.global.security.UserDetailsImpl;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {
	private final UserRepository userRepository;
	private final TaskRepository taskRepository;
	private final TicketRepository ticketRepository;
	private final CommentRepository commentRepository;

	// 티켓 등록
	@Transactional
	public ResponseEntity<?> createTicket(User user, TicketRequestDto ticketRequestDto) {
		System.out.println("--------user = " + user.getNickname());
		Task task = validateTask(ticketRequestDto.getTaskId());

		TaskUserRequestDto taskUserRequestDto = TaskUserRequestDto.create(user);
		User userList = TaskUser.create(taskUserRequestDto).getUser();
		Ticket ticket = Ticket.create(ticketRequestDto, userList, task);
		ticketRepository.save(ticket);
		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	}
	
	// 티켓 상세조회 (해당되는 티켓만 조회 // 로직 다시 짜보기)
	@Transactional(readOnly = true)
	public ResponseEntity<TicketResponseDto> getTicket(Long ticketId, User user, TicketRequestDto ticketRequestDto) {
		System.out.println("-----------------------user = " + user.getEmail());
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new CustomException(TICKET_NOT_FOUND));

		List<CommentResponseDto> commentList = getComment(ticket);
		TicketResponseDto ticketResponseDto = new TicketResponseDto(ticket, commentList);
		return ResponseEntity.ok().body(ticketResponseDto);

	}

	private List<CommentResponseDto> getComment(Ticket ticket) {
		List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
		List<Comment> commentList = commentRepository.findAllByTicketOrderByCreatedAtDesc(ticket);
		for (Comment c : commentList) {
			commentResponseDtoList.add(new CommentResponseDto(c));
		}
		return commentResponseDtoList;
	}



		// 티켓 수정하기
		@Transactional
		public ResponseEntity<?> updateTicket(Long ticketId,
			Long taskId, TicketRequestDto ticketRequestDto, UserDetailsImpl userDetails) {
			User user = userDetails.getUser();
			Task task = validateTask(ticketRequestDto.getTaskId());
			Ticket ticket =  ticketRepository.findById(ticketId).orElseThrow(
				() -> new CustomException(TICKET_NOT_FOUND));
			// validateTicket(task, ticket, userDetails);
			// ticketRepository.update(ticket);
			// ticketRepository.findById(ticketId).orElseThrow(() -> new CustomException(TICKET_NOT_FOUND));

			if (user.getEmail().equals(ticket.getLabelLeader()))
				ticket.update(ticketRequestDto, user);
			else throw new CustomException(UNAUTHORIZED_USER);
			return SuccessResponseDto.toResponseEntity(SuccessCode.UPDATED_SUCCESSFULLY);
		}
		// 티켓 삭제하기
		@Transactional
		public ResponseEntity<SuccessResponseDto> deleteTicket(Long ticketId, User user) {
			Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
				() -> new CustomException(TICKET_NOT_FOUND));
			if (user.getEmail().equals(ticket.getUserList().getEmail()))
				ticketRepository.deleteById(ticketId);
			else
				throw new CustomException(UNAUTHORIZED_USER);
			return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
		}
	/* == 반복 로직 == */
	// task 유무 확인
	private Task validateTask(Long taskId) {
		return taskRepository.findById(taskId).orElseThrow(() -> new CustomException(TASK_NOT_FOUND));
	}
	// ticket 유무 확인
	public Ticket getTicket(User user, Long ticketId) {
		return ticketRepository.findById(ticketId).orElseThrow(() -> new CustomException(TICKET_NOT_FOUND));
	}
	// User Email 유무 확인
	private User validateUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(
			() -> new CustomException(MEMBER_NOT_FOUND)
		);
	}
	// ticket 유효성 검사
	// private void validateTicket(Task task, Ticket ticket,  UserDetailsImpl userDetails, Long taskId, Long ticketId) {
	// // task에 해당 ticket이 있는지 검사
	// if (!ticket.getTicketId().equals(task.getTaskId()))
	// 	throw new CustomException(TICKET_NOT_FOUND);
	// // ticket 작성자와 요청자의 일치 여부 검사
	// // if (!ticket.getUserList().getUserId().equals(userDetails.getUser().getUserId()))
	// // 	throw new CustomException(UNAUTHORIZED_USER);
	// if (!ticket.getTicketId().equals(userDetails.getUser().getUserId()));
	// throw new CustomException(UNAUTHORIZED_USER);
	// 	// ticketId task의 ticketList 동일한 위치에
	// 	// if (!(task.getLabelLeadersList().equals(user.getEmail())) || task.getLabelLeadersList().contains(user.getEmail())) {
	//
	// 		// if (!ticket.getTicketId().equals(userDetails.getEmail()) || (!task.getTicketList().equals(taskId)))
	// 	// if (!ticket.getTicketId().equals(task.getTicketList()))
	// 	// 	throw new CustomException(TICKET_NOT_FOUND);
	// }

	// 권한 부여
	private void validateExistUser(Task task, User user) {
		if (!(task.getLabelLeadersList().equals(user.getEmail())) || task.getLabelLeadersList().contains(user.getEmail())) {
			throw new CustomException(UNAUTHORIZED_USER);
		}
	}

	// 	if (!ticket.getTeamLeader().equals(userDetails.getUser().getUserId()))
	// 		// equals(userDetails.getUser().getUserId()))
	// 		throw new CustomException(UNAUTHORIZED_USER);
	// }
	private void validateTaskLeader(Task task, UserDetailsImpl userDetails) {
	}

	private void validateTeamLeader(Ticket ticket, UserDetailsImpl userDetails) {
	}

	private Label validateExistTeam(Label label, User user) {
		if(!label.getLabelLeader().contains(user.toString())) {
			throw new CustomException(UNAUTHORIZED_USER);
		}
		return label;
	}



	// private void validateExistMember(Task task, TaskUser taskUser) {
	// 	if (!task.getTaskUserList().contains(taskUser)) {
	// 		throw new CustomException(UNAUTHORIZED_USER);
	// 	}
	// }

}
