package com.sparta.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "파일 업로드 응답 DTO")
public class FileDTO {
    @Schema(description = "기존 파일 이름", example = "test.jpg")
    private String originalFileName;

    @Schema(description = "저장된 파일 이름", example = "UUID_test.jpg")
    private String savedName;

    @Schema(description = "파일 경로", example = "/files/UUID_test.jpg")
    private String filePath;

    @Schema(description = "설명", example = "이 파일은 테스트 파일입니다.")
    private String description;
}
