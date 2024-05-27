package com.sparta.schedule.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.schedule.dto.ScheduleRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String contents;

	@OneToMany(mappedBy = "schedule", orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@Builder
	public Schedule(User user, String title, String contents) {
		this.user = user;
		this.title = title;
		this.contents = contents;
	}

	public void update(ScheduleRequestDTO requestDTO) {
		this.title = requestDTO.getTitle();
		this.contents = requestDTO.getContents();
	}
}
