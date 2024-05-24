package com.sparta.schedule.dto;

import com.sparta.schedule.entity.User;
import com.sparta.schedule.entity.UserRole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "유저 요청 DTO")
public class UserRequestDTO {

	@NotBlank
	@Size(min = 4, max = 10)
	@Pattern(regexp = "^[a-z0-9]*$", message = "영문 소문자와 숫자만 입력 가능합니다.")
	@Schema(description = "username", example = "test123")
	private String username;

	@NotBlank
	@Size(min = 8, max = 15)
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영문 대소문자와 숫자만 입력 가능합니다.")
	@Schema(description = "password", example = "password123")
	private String password;

	@NotBlank
	@Schema(description = "user role", example = "ROLE_USER")
	private String userRole;

	public User toEntity(String password, UserRole userRole) {
		return User.builder()
			.username(username)
			.password(password)
			.userRole(userRole)
			.build();
	}
}
