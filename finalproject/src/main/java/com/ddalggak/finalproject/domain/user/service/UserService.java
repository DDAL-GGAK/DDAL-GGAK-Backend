package com.ddalggak.finalproject.domain.user.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddalggak.finalproject.domain.user.dto.UserRequestDto;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.domain.user.exception.UserException;
import com.ddalggak.finalproject.domain.user.repository.UserRepository;
import com.ddalggak.finalproject.global.dto.SuccessCode;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;
import com.ddalggak.finalproject.global.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public ResponseEntity<?> signup(UserRequestDto userRequestDto) {
		String email = userRequestDto.getEmail();

		Optional<User> foundUser = userRepository.findByEmail(email);

		if (foundUser.isPresent()) {
			throw new UserException(ErrorCode.DUPLICATE_MEMBER);
		}

		String nickname = "anonymous";
		String password = passwordEncoder.encode(userRequestDto.getPassword());

		User user = new User(email, nickname, password);

		userRepository.save(user);

		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);

	}

}
