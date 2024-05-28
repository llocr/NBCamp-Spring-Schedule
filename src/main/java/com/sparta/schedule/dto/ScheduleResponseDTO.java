package com.sparta.schedule.dto;

import java.time.LocalDateTime;

import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.UploadFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "일정 응답 DTO")
public class ScheduleResponseDTO {
	private String username;
	private Long id;
	private String title;
	private String contents;
	private String file;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;

	public ScheduleResponseDTO(Schedule schedule) {
		this.username = schedule.getUser().getUsername();
		this.id = schedule.getId();
		this.title = schedule.getTitle();
		this.contents = schedule.getContents();
		this.createDate = schedule.getCreateDate();
		this.modifyDate = schedule.getModifyDate();

		UploadFile file = schedule.getFile();
		if (file != null) {
			this.file = file.getName();
		}
	}

}
