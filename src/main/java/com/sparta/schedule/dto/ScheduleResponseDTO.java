package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDTO {
    private Long id;
    private String title;
    private String contents;
    private String nickname;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    public ScheduleResponseDTO(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.nickname = schedule.getNickname();
        this.createDate = schedule.getCreateDate();
        this.modifyDate = schedule.getModifyDate();
    }

}
