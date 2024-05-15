package com.sparta.schedule.exception;

public class ScheduleNotFoundException extends IllegalArgumentException{
    public ScheduleNotFoundException() {
        super("일정을 찾을 수 없습니다.");
    }
}
