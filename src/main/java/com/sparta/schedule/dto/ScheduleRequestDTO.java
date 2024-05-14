package com.sparta.schedule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDTO {
    private String title;
    private String contents;
    private String nickname;
    private String password;
}
