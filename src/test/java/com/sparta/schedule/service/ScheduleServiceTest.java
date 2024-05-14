package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDTO;
import com.sparta.schedule.dto.ScheduleResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
        assertThat(requestDTO.getTitle()).isEqualTo(scheduleResponseDTO.getTitle());
        assertThat(requestDTO.getContents()).isEqualTo(scheduleResponseDTO.getContents());
        assertThat(requestDTO.getNickname()).isEqualTo(scheduleResponseDTO.getNickname());
    }

    @Test
    @DisplayName("선택된 스케줄 조회 성공 테스트")
    void 선택된스케줄조회성공() {
        //given
        Long id = 2L;

        //when
        ScheduleResponseDTO findSchedule = scheduleService.getSchedule(id);

        //then
        assertThat(id).isEqualTo(findSchedule.getId());
    }

    @Test
    @DisplayName("선택된 스케줄 조회 실패 테스트")
    void 선택된스케줄조회실패() {
        //given
        Long id = 0L;

        //when
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () -> scheduleService.getSchedule(id));

        //then
        assertThat(illegalArgumentException.getMessage()).isEqualTo("해당 스케줄이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("등록된 전체 스케줄 조회 테스트")
    void 전체스케줄조회테스트() {
        //when
        List<ScheduleResponseDTO> scheduleList = scheduleService.getAllSchedules();

        //then
        assertThat(scheduleList).isNotEmpty();
    }

    @Test
    @Transactional
    @DisplayName("스케줄 수정 테스트")
    void 스케줄수정성공테스트() {
        //given
        Long id = 2L;
        ScheduleRequestDTO requestDTO = new ScheduleRequestDTO();
        requestDTO.setTitle("수정된 테스트입니다.");
        requestDTO.setContents("오늘의 스케줄은 무엇일까요?");
        requestDTO.setNickname("heesue");
        requestDTO.setPassword("1234");

        //when
        ScheduleResponseDTO updateSchedule = scheduleService.updateSchedule(id, requestDTO);

        //then
        assertThat(requestDTO.getTitle()).isEqualTo(updateSchedule.getTitle());
        assertThat(requestDTO.getContents()).isEqualTo(updateSchedule.getContents());
        assertThat(requestDTO.getNickname()).isEqualTo(updateSchedule.getNickname());
    }

    @Test
    @Transactional
    @DisplayName("스케줄 수정 실패 테스트_비밀번호오류")
    void 스케줄수정실패테스트_비밀번호오류() {
        //given
        Long id = 2L;
        ScheduleRequestDTO requestDTO = new ScheduleRequestDTO();
        requestDTO.setTitle("수정된 테스트입니다.");
        requestDTO.setContents("오늘의 스케줄은 무엇일까요?");
        requestDTO.setNickname("heesue");
        requestDTO.setPassword("12345");

        //when
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () -> scheduleService.updateSchedule(id, requestDTO));

        //then
        assertThat(illegalArgumentException.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @Transactional
    @DisplayName("스케줄 수정 실패 테스트_해당스케줄없음")
    void 스케줄수정실패테스트_해당스케줄없음() {
        //given
        Long id = 0L;
        ScheduleRequestDTO requestDTO = new ScheduleRequestDTO();
        requestDTO.setTitle("수정된 테스트입니다.");
        requestDTO.setContents("오늘의 스케줄은 무엇일까요?");
        requestDTO.setNickname("heesue");
        requestDTO.setPassword("1234");

        //when
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () -> scheduleService.updateSchedule(id, requestDTO));

        //then
        assertThat(illegalArgumentException.getMessage()).isEqualTo("해당 스케줄이 존재하지 않습니다.");
    }
}