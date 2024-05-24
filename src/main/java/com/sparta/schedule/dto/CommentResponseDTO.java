package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDTO {
	private Long id;
	private String contents;
	private String username;
	private Long scheduleId;

	public CommentResponseDTO(Comment comment) {
		this.id = comment.getId();
		this.contents = comment.getContents();
		this.username = comment.getUser().getUsername();
		this.scheduleId = comment.getSchedule().getId();
	}
}
