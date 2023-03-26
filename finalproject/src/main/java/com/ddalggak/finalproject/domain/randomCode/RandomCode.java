package com.ddalggak.finalproject.domain.randomCode;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60 * 5 * 1000L)
public class RandomCode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String randomCode;

	public RandomCode(String email, String randomCode) {
		this.email = email;
		this.randomCode = randomCode;
	}
}
