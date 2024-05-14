package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDTO;
import com.sparta.schedule.dto.ScheduleResponseDTO;
import com.sparta.schedule.entity.Schedule;
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

    @Transactional
    public ScheduleResponseDTO saveSchedule(ScheduleRequestDTO requestDTO) {
        Schedule schedule = new Schedule(requestDTO);
        Schedule saveSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDTO(saveSchedule);
    }

    public List<ScheduleResponseDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAll();

        if(!scheduleList.isEmpty()) {
            return scheduleList.stream().map(ScheduleResponseDTO::new).toList();
        } else {
            throw new IllegalArgumentException("등록된 스케줄이 없습니다.");
        }
    }

    public ScheduleResponseDTO getSchedule(Long id) {
        Schedule schedule = findScheduleById(id);
        return new ScheduleResponseDTO(schedule);
    }

    private Schedule findScheduleById(Long id) {
        Optional<Schedule> findSchedule = scheduleRepository.findById(id);

        if(findSchedule.isPresent()) {
            return findSchedule.get();
        } else {
            throw new IllegalArgumentException("해당 스케줄이 존재하지 않습니다.");
        }
    }
}
