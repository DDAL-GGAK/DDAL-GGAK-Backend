package com.ddalggak.finalproject.domain.user.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddalggak.finalproject.domain.user.entity.User;
import com.ddalggak.finalproject.domain.user.profile.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	void deleteByUser(User user);
}
