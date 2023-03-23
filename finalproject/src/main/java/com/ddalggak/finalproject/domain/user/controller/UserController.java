package com.ddalggak.finalproject.domain.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ddalggak.finalproject.domain.user.dto.EmailRequestDto;
import com.ddalggak.finalproject.domain.user.dto.UserRequestDto;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.domain.user.repository.UserRepository;
import com.ddalggak.finalproject.domain.user.service.UserService;
import com.ddalggak.finalproject.global.dto.SuccessCode;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;
import com.ddalggak.finalproject.global.error.ErrorCode;
import com.ddalggak.finalproject.global.error.ErrorResponse;
import com.ddalggak.finalproject.global.jwt.JwtUtil;
import com.ddalggak.finalproject.global.mail.MailService;
import com.ddalggak.finalproject.global.security.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	private final MailService mailService;

	@PostMapping("/email")
	public ResponseEntity<?> emailAuthentication(@Valid @RequestBody EmailRequestDto emailRequestDto,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<ObjectError> list = bindingResult.getAllErrors();
			for (ObjectError e : list) {
				System.out.println(e.getDefaultMessage());
			}

		}
		mailService.sendMail(emailRequestDto.getEmail());
		return SuccessResponseDto.toResponseEntity(SuccessCode.SUCCESS_SEND);

	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody UserRequestDto userRequestDto,
		BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<ObjectError> list = bindingResult.getAllErrors();
			for (ObjectError e : list) {
				System.out.println(e.getDefaultMessage());
			}

		}

		userService.signup(userRequestDto);

		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
		userService.login(userRequestDto, response);
		return SuccessResponseDto.toResponseEntity(SuccessCode.SUCCESS_LOGIN);

	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		String token = jwtUtil.resolveToken(request);
		Claims claims;
		if (token != null) {
			if (jwtUtil.validateToken(token)) {
				claims = jwtUtil.getUserInfo(token);
			} else {
				return ErrorResponse.from(ErrorCode.INVALID_REQUEST);
			}
			User user = userRepository.findByEmail(claims.getSubject())
				.orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
			if (user.equals(userDetails.getUser())) {
				jwtUtil.logoutToken(user.getUserId());
				// jwtUtil.deleteToken(user.getUserId());
			} else {
				return ErrorResponse.from(ErrorCode.INVALID_REQUEST);
			}
		} else {
			return ErrorResponse.from(ErrorCode.INVALID_REQUEST);
		}
		return SuccessResponseDto.toResponseEntity(SuccessCode.SUCCESS_LOGOUT);
	}

	@PutMapping("{nickname}")
	public ResponseEntity<?> updateNickname(@PathVariable String nickname,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		userService.updateNickname(nickname, userDetails.getEmail());
		return SuccessResponseDto.toResponseEntity(SuccessCode.SUCCESS_UPLOAD);
	}

	@PutMapping("/profile")
	public ResponseEntity<?> updateProfile(@RequestPart(value = "image") MultipartFile image,
		@AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
		userService.updateProfile(image, userDetails.getEmail());
		return SuccessResponseDto.toResponseEntity(SuccessCode.SUCCESS_UPLOAD);
	}

	@GetMapping("/mypage")
	public ResponseEntity<?> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return userService.getMyPage(userDetails.getEmail());
	}

}