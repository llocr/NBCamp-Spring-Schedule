package com.sparta.schedule.controller;

import com.sparta.schedule.dto.PasswordDTO;
import com.sparta.schedule.dto.ScheduleRequestDTO;
import com.sparta.schedule.dto.ScheduleResponseDTO;
import com.sparta.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedule")
    public ScheduleResponseDTO saveSchedule(@RequestBody ScheduleRequestDTO requestDTO) {
        return scheduleService.saveSchedule(requestDTO);
    }

    @GetMapping("/schedule")
    public List<ScheduleResponseDTO> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/schedule/{id}")
    public ScheduleResponseDTO getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    @PutMapping("/schedule/{id}")
    public ScheduleResponseDTO updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDTO requestDTO){
        return scheduleService.updateSchedule(id, requestDTO);
    }

    @DeleteMapping("/schedule/{id}")
    public Long deleteSchedule(@PathVariable Long id, @RequestBody PasswordDTO passwordDTO) {
        return scheduleService.deleteSchedule(id, passwordDTO.getPassword());
    }
}
