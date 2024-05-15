package com.sparta.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordDTO {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    public String password;
}
