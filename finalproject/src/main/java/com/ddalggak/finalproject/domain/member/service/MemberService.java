package com.ddalggak.finalproject.domain.member.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddalggak.finalproject.domain.member.dto.MemberRequestDto;
import com.ddalggak.finalproject.domain.member.entity.Member;
import com.ddalggak.finalproject.domain.member.exception.MemberException;
import com.ddalggak.finalproject.domain.member.repository.MemberRepository;
import com.ddalggak.finalproject.global.dto.SuccessCode;
import com.ddalggak.finalproject.global.dto.SuccessResponseDto;
import com.ddalggak.finalproject.global.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public ResponseEntity<?> signup(MemberRequestDto memberRequestDto) {
		String email = memberRequestDto.getEmail();

		Optional<Member> foundMember = memberRepository.findByEmail(email);

		if (foundMember.isPresent()) {
			throw new MemberException(ErrorCode.DUPLICATE_MEMBER);
		}

		String nickname = "anonymous";
		String password = passwordEncoder.encode(memberRequestDto.getPassword());

		Member member = new Member(email, nickname, password);

		memberRepository.save(member);

		return SuccessResponseDto.toResponseEntity(SuccessCode.CREATED_SUCCESSFULLY);

	}

}
