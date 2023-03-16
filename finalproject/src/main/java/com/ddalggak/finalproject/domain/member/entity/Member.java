package com.ddalggak.finalproject.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long member_id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String password;

	public Member(String email, String nickname, String password) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}
}