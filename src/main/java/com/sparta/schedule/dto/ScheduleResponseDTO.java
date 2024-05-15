package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDTO {
    private Long id;
    private String title;
    private String contents;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public ScheduleResponseDTO(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.email = schedule.getEmail();
        this.createDate = schedule.getCreateDate();
        this.modifyDate = schedule.getModifyDate();
    }

}
