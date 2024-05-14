package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDTO;
import com.sparta.schedule.dto.ScheduleResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScheduleServiceTest {
    @Autowired
    private ScheduleService scheduleService;

    @Test
    @Transactional
    @DisplayName("스케줄 저장 테스트")
    void 스케줄저장() {
        //given
        ScheduleRequestDTO requestDTO = new ScheduleRequestDTO();
        requestDTO.setTitle("테스트입니다.");
        requestDTO.setContents("오늘의 스케줄은 없습니다.");
        requestDTO.setNickname("heesue");
        requestDTO.setPassword("1234");

        //when
        ScheduleResponseDTO scheduleResponseDTO = scheduleService.saveSchedule(requestDTO);

        //then
        assertEquals(requestDTO.getNickname(), scheduleResponseDTO.getNickname());
        assertEquals(requestDTO.getTitle(), scheduleResponseDTO.getTitle());
        assertEquals(requestDTO.getContents(), scheduleResponseDTO.getContents());
    }
}