package com.ddalggak.finalproject.global.jwt.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddalggak.finalproject.global.jwt.token.dto.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
