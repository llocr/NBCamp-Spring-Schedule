package com.sparta.schedule.dto;

import java.time.LocalDateTime;

import com.sparta.schedule.entity.User;
import com.sparta.schedule.entity.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "유저 응답 DTO")
public class UserResponseDTO {
	private Long id;
	private String username;
	private UserRole userRole;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;

	public UserResponseDTO(User saveUser) {
		this.id = saveUser.getId();
		this.username = saveUser.getUsername();
		this.userRole = saveUser.getUserRole();
		this.createDate = saveUser.getCreateDate();
		this.modifyDate = saveUser.getModifyDate();
	}
}
