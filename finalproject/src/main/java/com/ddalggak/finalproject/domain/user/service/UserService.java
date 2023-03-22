package com.ddalggak.finalproject.domain.user.service;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ddalggak.finalproject.domain.user.dto.UserRequestDto;
import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.domain.user.exception.UserException;
import com.ddalggak.finalproject.domain.user.profile.entity.Profile;
import com.ddalggak.finalproject.domain.user.profile.repository.ProfileRepository;
import com.ddalggak.finalproject.domain.user.repository.UserRepository;
import com.ddalggak.finalproject.global.error.CustomException;
import com.ddalggak.finalproject.global.error.ErrorCode;
import com.ddalggak.finalproject.global.jwt.JwtUtil;
import com.ddalggak.finalproject.infra.aws.S3Uploader;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final ProfileRepository profileRepository;
	private final S3Uploader s3Uploader;

	private long fileSizeLimit = 10 * 1024 * 1024;//10메가바이트/킬로바이트/바이트

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

	@Transactional
	public void login(UserRequestDto userRequestDto, HttpServletResponse response) {
		String email = userRequestDto.getEmail();

		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isEmpty()) {
			throw new UserException(ErrorCode.UNAUTHORIZED_MEMBER);
		}

		User user = optionalUser.get();
		String password = userRequestDto.getPassword();
		String dbPassword = user.getPassword();

		if (!passwordEncoder.matches(password, dbPassword)) {
			throw new UserException(ErrorCode.INVALID_PASSWORD);
		}

		response.addHeader(JwtUtil.AUTHORIZATION_HEADER,
			jwtUtil.createAccessToken(user.getEmail(), user.getRole()));

	}

	@Transactional
	public void updateProfile(MultipartFile image, HttpServletRequest request, String email) throws IOException {
		String token = jwtUtil.resolveToken(request);
		Claims claims;
		fileSizeCheck(image);
		fileCheck(image);

		if (token != null) {
			if (jwtUtil.validateToken(token)) {
				claims = jwtUtil.getUserInfo(token);
			} else {
				throw new UserException(ErrorCode.INVALID_REQUEST);
			}
			User user = userRepository.findByEmail(claims.getSubject())
				.orElseThrow(() -> new CustomException(ErrorCode.EMPTY_CLIENT));
			if (user.getEmail().equals(email)) {
				String storedFileName = s3Uploader.upload(image, "profile");
				profileRepository.deleteByUser(user);
				profileRepository.save(new Profile(user, storedFileName));
			} else {
				throw new UserException(ErrorCode.INVALID_REQUEST);
			}
		} else {
			throw new UserException(ErrorCode.INVALID_REQUEST);
		}

	}

	private boolean fileCheck(MultipartFile file) {
		String fileName = StringUtils.getFilenameExtension(file.getOriginalFilename());

		if (fileName != null) {
			String exe = fileName.toLowerCase();
			if (exe.equals("jpg") || exe.equals("png") || exe.equals("jpeg") || exe.equals("webp")) {
				return false;
			}
		}
		return true;
	}

	private void fileSizeCheck(MultipartFile image) {
		long fileSize = image.getSize();

		if (fileSize > fileSizeLimit) {
			throw new IllegalArgumentException("총 용량 10MB이하만 업로드 가능합니다");
		}
	}
}
