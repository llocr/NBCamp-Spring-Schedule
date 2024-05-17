package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "schedule")
public class Schedule extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String contents;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Builder
	public Schedule(String title, String contents, String email, String password) {
		this.title = title;
		this.contents = contents;
		this.email = email;
		this.password = password;
	}

	public void update(ScheduleRequestDTO requestDTO) {
		this.title = requestDTO.getTitle();
		this.contents = requestDTO.getContents();
		this.email = requestDTO.getEmail();
	}
}
