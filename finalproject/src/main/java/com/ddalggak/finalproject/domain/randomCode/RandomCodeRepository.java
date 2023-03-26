package com.ddalggak.finalproject.domain.randomCode;

import org.springframework.data.repository.CrudRepository;

public interface RandomCodeRepository extends CrudRepository<RandomCode, Long> {
	RandomCode findByRandomCode(String randomCode);
}
