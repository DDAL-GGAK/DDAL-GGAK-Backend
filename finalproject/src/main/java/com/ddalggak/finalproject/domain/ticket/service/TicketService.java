package com.ddalggak.finalproject.domain.ticket.service;

import static com.ddalggak.finalproject.global.error.ErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.ddalggak.finalproject.domain.ticket.entity.TicketStatus;
import com.ddalggak.finalproject.domain.ticket.repository.TicketRepository;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.domain.user.repository.UserRepository;
import com.ddalggak.finalproject.global.dto.SuccessCode;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;
import com.ddalggak.finalproject.global.error.CustomException;
import com.ddalggak.finalproject.global.error.ErrorCode;
import com.ddalggak.finalproject.global.security.UserDetailsImpl;

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
	// @Transactional
	// public ResponseEntity<?> createTicket(User user, TicketRequestDto ticketRequestDto) {
	// 	System.out.println("--------user = " + user.getNickname());
	// 	ticketRepository.saveAndFlush(new Ticket(ticketRequestDto));
	// 	ResponseEntity.ok().body(TicketResponseDto.of(ticket));
	// 	return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	// 	// return ResponseEntity.createTicket(ticketResponseDto, userDetails.getUser());
	// }
	@Transactional
	public ResponseEntity<?> createTicket(TicketRequestDto ticketRequestDto, Long taskId,
		User user) {//, TicketStatus status) {
		Task task = validateTask(taskId);
		validateExistMember(task, TaskUser.create(task, user));
		if (!(task.getTaskLeader().equals(user.getEmail()))) {
			throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
		}
		TaskUserRequestDto taskUserRequestDto = TaskUserRequestDto.create(user);
		TaskUser taskUser = TaskUser.create(taskUserRequestDto);
		Ticket ticket = Ticket.create(ticketRequestDto, user, task);
		ticket.setTaskLeader(user.getEmail());
		ticketRepository.save(ticket);
		return SuccessResponseDto.toResponseEntity(SuccessCode.SUCCESS_UPLOAD);
	}


		// if (task.getProject().getProjectLeader().equals(user.getEmail()) || task.getTaskLeader()
			// 	.equals(user.getEmail())) {
			// 	taskRepository.delete(task);
			// 	// Long task = taskId;
			// 	// validateExistMember(task, TaskUser.create(task, user));
			// 	// if (!(task.getTaskLeader().equals(user.getEmail()))) || task.getTaskLeaderList()
			// 	// 	.contains(user.getEmail())) {
			// 	// 	throw new CustomException(UNAUTHORIZED_USER);
			// 	}
			// 	Ticket ticket = ticketRepository.saveAndFlush(new Ticket(ticketRequestDto, user));//, status));
			// 	// Ticket ticket = new Ticket(user, ticketRequestDto, status);
			// 	// ticketRepository.save(ticket);		// ResponseEntity.ok().body(TicketResponseDto.of(ticket));
			// 	return SuccessResponseDto.toResponseEntity(SuccessCode.SUCCESS_UPLOAD);
			// }

		// public ResponseEntity<TicketResponseDto> createTicket(
		// 	UserDetailsImpl userDetails,
		// 	Long taskId,
		// 	TicketResponseDto ticketResponseDto) {
		// 	// 유저
		// 	User user = userDetails.getUser();
		// 	// 테스크
		// 	Task task = getTast(taskId);
		// 	// ticket 작성
		// 	Ticket ticket = new Ticket(user, task, ticketResponseDto);
		// 	ticketRepository.save(ticket);
		// 	// 상태 변환
		// 	return ResponseEntity.ok().body(TicketResponseDto.from(ticket.get()));
		// 	//TicketResponseDto.of(SuccessCode.CREATED_SUCCESSFULLY);
		// }
		//
		// // 티켓 전체조회 (테스크에 들어갈 내용)
		// // 티켓 상세조회 (해당되는 티켓만 조회 // 로직 다시 짜보기)
		// @Transactional(readOnly = true)
		// public List<TicketResponseDto> getTickets() {
		// 	List<TicketResponseDto> list = new ArrayList<>();
		// 	List<Ticket> ticketList;
		// 	ticketList = ticketRepository.findAll();
		// 	for (Ticket i : ticketList) {
		// 		list.add(new TicketResponseDto(i));
		// 	}
		// 	return list;
		// }
		//

		// // 티켓 상세 조회
		// // (태스크가 존재하는 지부터 들어가기
		// @Transactional
		// public ResponseEntity<TicketResponseDto> viewTicket(
		// 	Long taskId, Long ticketId, String email ) {
		// 	// 테스크 안에 있는 일반 유저를 받아 오고 싶은데, 이렇게 하는게 맞나요?
		// 	User user = validateUserByEmail(email);
		// 	Task task = validateTask(taskId);
		// 	Ticket ticket = validateTicket(ticketId);
		// 	validateUserByEmail(ticket, Ticket.create(ticket, user) );
		// 	// validateTeamLeader(user.getUserId());
		// 	// return TicketResponseDto.toResponseEntity(ticket);
		//
		// 		ticketRepository.findById(ticketId).orElseThrow(
		// 		() ->  new CustomException(TICKET_NOT_FOUND));
		// 	List<CommentResponseDto> commentList = getComment(ticket);
		// 	TicketResponseDto ticketResponseDto = new TicketResponseDto(ticket); //, commentList);
		// 	return ResponseEntity.ok().body(ticketResponseDto);
		// 	// return ResponseEntity.status(HttpStatus.OK).body(TicketResponseDto.of(ticket));
		// 	// List<CommentResponseDto> commentList = getComment(ticket);
		// 	// ticketResponseDto = new TicketResponseDto(ticket, commentList);
		// 	// return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
		// }
	// 티켓 상세 조회
	@Transactional
	public ResponseEntity<TicketResponseDto> viewTicket(Long ticketId, String email) {
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
			() -> new CustomException(TICKET_NOT_FOUND));

		List<CommentResponseDto> commentList = getComment(ticket);
		TicketResponseDto ticketResponseDto = new TicketResponseDto(ticket); //, commentList);
		return ResponseEntity.ok().body(ticketResponseDto);
		// return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);
	}
		// 티켓에 있는 댓글 가져오기
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
		public ResponseEntity<SuccessResponseDto> updateTicket(Long ticketId,
			Long taskId, TicketRequestDto ticketRequestDto, UserDetailsImpl userDetails, TicketStatus status) {
			User user = userDetails.getUser();
			// Task task = taskRepository.findById(taskId)
			Ticket ticket =  ticketRepository.findById(ticketId).orElseThrow(
				() -> new CustomException(TICKET_NOT_FOUND));
			// validateTicket(task, ticket, userDetails);
			// ticketRepository.update(ticket);
			ticketRepository.findById(ticketId).orElseThrow(() -> new CustomException(TICKET_NOT_FOUND));

			if (user.getEmail().equals(ticket.getTeamLeader()))
				ticket.update(ticketRequestDto, user);
			else throw new CustomException(UNAUTHORIZED_USER);
			return SuccessResponseDto.toResponseEntity(SuccessCode.UPDATED_SUCCESSFULLY);
		}

		// 티켓 삭제하기
		@Transactional
		public ResponseEntity<?> deleteTicket(Long ticketId, Long taskId, User user) {
			Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
				() -> new CustomException(TICKET_NOT_FOUND));
			if (user.getEmail().equals(ticket.getTeamLeader()))
				ticketRepository.deleteById(ticketId);
			else throw new CustomException(UNAUTHORIZED_USER);
			return SuccessResponseDto.toResponseEntity(SuccessCode.DELETED_SUCCESSFULLY);
		}

	/* == 반복 로직 == */
	// task 유무 확인
	private Task validateTask(Long taskId) {
		return taskRepository.findById(taskId).orElseThrow(() -> new CustomException(TASK_NOT_FOUND));
	}

	private Ticket validateTicket(Long ticketId) {
		return ticketRepository.findById(ticketId).orElseThrow(() -> new CustomException(TICKET_NOT_FOUND));
	}

	// User Email 유무 확인
	private User validateUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(
			() -> new CustomException(MEMBER_NOT_FOUND)
		);
	}
//ticketid랑 task의 ticketList 동일한지 검사 필요
	// ticket 유효성 검사
	private void validateTicket(Task task, Ticket ticket, User user, Long taskId, Long ticketId) {
		if (!ticket.getTicketId().equals(user.getEmail()) || (!task.getTicketList().equals(taskId)))
		// if (!ticket.getTicketId().equals(task.getTicketList()))
			throw new CustomException(TICKET_NOT_FOUND);
	}

	// 	if (!ticket.getTeamLeader().equals(userDetails.getUser().getUserId()))
	// 		// equals(userDetails.getUser().getUserId()))
	// 		throw new CustomException(UNAUTHORIZED_USER);
	// }
	private void validateTaskLeader(Task task, UserDetailsImpl userDetails) {
	}

	private void validateTeamLeader(Ticket ticket, UserDetailsImpl userDetails) {
	}

	private void validateExistMember(Task task, TaskUser taskUser) {
		if (!task.getTaskUserList().contains(taskUser)) {
			throw new CustomException(UNAUTHORIZED_USER);
		}
	}

}