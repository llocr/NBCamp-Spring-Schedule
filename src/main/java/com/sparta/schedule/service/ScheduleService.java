package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDTO;
import com.sparta.schedule.dto.ScheduleResponseDTO;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.exception.InvalidPasswordException;
import com.sparta.schedule.exception.ScheduleNotFoundException;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /*
    1. 일정 추가
     */
    @Transactional
    public ScheduleResponseDTO saveSchedule(ScheduleRequestDTO requestDTO) {
        Schedule schedule = new Schedule(requestDTO);
        Schedule saveSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDTO(saveSchedule);
    }

    /*
    2. 일정 전체 조회
     */
    public List<ScheduleResponseDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAll();

        if(!scheduleList.isEmpty()) {
            return scheduleList.stream().map(ScheduleResponseDTO::new).toList();
        } else {
            throw new IllegalArgumentException("등록된 스케줄이 없습니다.");
        }
    }

    /*
    3. 선택한 일정 조회
     */
    public ScheduleResponseDTO getSchedule(Long id) {
        Schedule schedule = findScheduleById(id);
        return new ScheduleResponseDTO(schedule);
    }

    /*
    4. 선택한 일정 수정
     */
    @Transactional
    public ScheduleResponseDTO updateSchedule(Long id, ScheduleRequestDTO requestDTO) {
        Schedule schedule = findScheduleById(id);
        validatePassword(schedule, requestDTO.getPassword());

        schedule.update(requestDTO);
        return new ScheduleResponseDTO(schedule);
    }

    /*
    5. 선택한 일정 삭제
     */
    @Transactional
    public Long deleteSchedule(Long id, String password) {
        Schedule schedule = findScheduleById(id);
        validatePassword(schedule, password);

        scheduleRepository.delete(schedule);
        return schedule.getId();
    }

    /*
    아이디로 Schedule 검색
     */
    private Schedule findScheduleById(Long id) {
        Optional<Schedule> findSchedule = scheduleRepository.findById(id);

        if(findSchedule.isPresent()) {
            return findSchedule.get();
        } else {
            throw new ScheduleNotFoundException();
        }
    }

    /*
    비밀번호 유효성 확인
     */
    private void validatePassword(Schedule schedule, String password) {
        if (!schedule.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }
    }
}
