package com.ddalggak.finalproject.domain.user.profile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.ddalggak.finalproject.domain.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String imageUrl;

	@OneToOne
	@JoinColumn(name = "userId")
	private User user;

	public Profile(User user, String imageUrl) {
		this.user = user;
		this.imageUrl = imageUrl;
	}
}