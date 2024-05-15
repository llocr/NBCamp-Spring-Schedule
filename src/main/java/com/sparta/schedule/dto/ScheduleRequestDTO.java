package com.sparta.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "일정 요청 DTO")
public class ScheduleRequestDTO {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 1, max = 200, message = "제목은 1자 이상, 200자 이내로 입력해주세요.")
    @Schema(description = "schedule title", example = "제목")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 1, message = "내용은 1자 이상 입력해주세요.")
    @Schema(description = "schedule contents", example = "내용")
    private String contents;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식을 지켜주세요.")
    @Schema(description = "email", example = "1234@gmail.com")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "password", example = "password")
    private String password;
}
