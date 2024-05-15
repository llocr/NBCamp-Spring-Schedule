package com.sparta.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "비밀번호 요청 DTO")
public class PasswordDTO {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "password", example = "password")
    public String password;
}
