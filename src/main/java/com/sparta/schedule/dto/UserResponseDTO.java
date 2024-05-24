package com.sparta.schedule.dto;

import java.time.LocalDateTime;

import com.sparta.schedule.entity.User;
import com.sparta.schedule.entity.UserRole;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
