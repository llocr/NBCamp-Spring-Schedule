package com.sparta.schedule.exception;

public class ScheduleNotFoundException extends IllegalArgumentException{
    public ScheduleNotFoundException() {
        super("해당 스케줄이 존재하지 않습니다.");
    }
}
