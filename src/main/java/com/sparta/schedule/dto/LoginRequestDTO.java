package com.sparta.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "로그인 요청 DTO")
public class LoginRequestDTO {
	@Schema(description = "username", example = "test123")
	private String username;

	@Schema(description = "password", example = "password123")
	private String password;
}
