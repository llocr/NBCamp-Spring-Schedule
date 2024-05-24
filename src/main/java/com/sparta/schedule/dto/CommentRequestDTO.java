package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "댓글 요청 DTO")
public class CommentRequestDTO {
	@NotBlank(message = "내용을 입력해주세요.")
	@Schema(description = "comment contents", example = "내용입니다")
	private String contents;

	public Comment toEntity(CommentRequestDTO requestDTO, User user, Schedule schedule) {
		return Comment.builder()
			.contents(requestDTO.getContents())
			.user(user)
			.schedule(schedule)
			.build();
	}
}
