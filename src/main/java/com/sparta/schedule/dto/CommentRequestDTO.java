package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
	@NotBlank(message = "내용을 입력해주세요.")
	private String contents;

	public Comment toEntity(CommentRequestDTO requestDTO, User user, Schedule schedule) {
		return Comment.builder()
			.contents(requestDTO.getContents())
			.user(user)
			.schedule(schedule)
			.build();
	}
}
