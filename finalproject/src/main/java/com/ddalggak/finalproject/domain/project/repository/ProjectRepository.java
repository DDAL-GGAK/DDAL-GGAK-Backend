package com.ddalggak.finalproject.domain.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ddalggak.finalproject.domain.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	@Query("select p from Project p join fetch p.projectUserList pu where pu.user.userId = :userId")
	List<Project> findAllByUserId(Long userId);

}
