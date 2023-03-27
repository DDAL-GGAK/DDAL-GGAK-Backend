package com.ddalggak.finalproject.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddalggak.finalproject.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	Optional<User> findByNickname(String nickname);

	// @Query("select u from Users u fetch join u.taskUserList ") //todo 3명 이상이면 3명만 가져와야 함
	// List<User> findTop3ByTaskUserList(String taskId);
}
