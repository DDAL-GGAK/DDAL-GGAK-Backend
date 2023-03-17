package com.ddalggak.finalproject.domain.task.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.ddalggak.finalproject.domain.project.entity.Project;
import com.ddalggak.finalproject.domain.user.entity.Label;
import com.ddalggak.finalproject.global.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long taskId;

	private String taskTitle;

	private double totalPriority;

	private double totalDifficulty;

	private LocalDateTime expiredAt;

	private final String taskLeader = "";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Project project;

	@OneToMany(mappedBy = "task")
	private List<Label> labelList = new ArrayList<>();

	@OneToMany(mappedBy = "task")
	private List<TaskUser> taskUserList = new ArrayList<>();

}
