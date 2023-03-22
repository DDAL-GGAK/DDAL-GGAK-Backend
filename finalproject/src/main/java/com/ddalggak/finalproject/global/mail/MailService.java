package com.ddalggak.finalproject.global.mail;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.domain.user.exception.UserException;
import com.ddalggak.finalproject.domain.user.repository.UserRepository;
import com.ddalggak.finalproject.global.error.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;
	private final UserRepository userRepository;

	public void sendMail(String email) {

		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isEmpty()) {
			throw new UserException(ErrorCode.UNAUTHORIZED_MEMBER);
		}

		ArrayList<String> toUserList = new ArrayList<>();

		toUserList.add(email);

		int toUserSize = toUserList.size();

		SimpleMailMessage simpleMessage = new SimpleMailMessage();

		simpleMessage.setTo((String[])toUserList.toArray(new String[toUserSize]));

		simpleMessage.setSubject("Welcome To DDAL-KKAK");

		simpleMessage.setText("메일 내용 적기");

		javaMailSender.send(simpleMessage);
	}
}
