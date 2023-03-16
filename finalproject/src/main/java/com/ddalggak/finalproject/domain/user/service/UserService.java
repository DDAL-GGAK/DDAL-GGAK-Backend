package com.ddalggak.finalproject.domain.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddalggak.finalproject.domain.user.dto.UserRequestDto;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.domain.user.exception.UserException;
import com.ddalggak.finalproject.domain.user.repository.UserRepository;
import com.ddalggak.finalproject.global.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void signup(UserRequestDto userRequestDto) {
		String email = userRequestDto.getEmail();

		Optional<User> foundUser = userRepository.findByEmail(email);

		if (foundUser.isPresent()) {
			throw new UserException(ErrorCode.DUPLICATE_MEMBER);
		}

		String nickname = "anonymous";
		String password = passwordEncoder.encode(userRequestDto.getPassword());

		User user = new User(email, nickname, password);

		userRepository.save(user);

	}

	public void login(UserRequestDto userRequestDto) {
		String email = userRequestDto.getEmail();

		Optional<User> optionalMember = userRepository.findByEmail(email);

		if (optionalMember.isEmpty()) {
			throw new UserException(ErrorCode.UNAUTHORIZED_MEMBER);
		}

		String password = userRequestDto.getPassword();
		String dbPassword = optionalMember.get().getPassword();

		if(!passwordEncoder.matches(password, dbPassword)){
			throw new UserException(ErrorCode.INVALID_PASSWORD);
		}

	}

}
