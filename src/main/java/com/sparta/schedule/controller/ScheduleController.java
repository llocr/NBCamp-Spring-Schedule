package com.sparta.schedule.controller;

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
}
