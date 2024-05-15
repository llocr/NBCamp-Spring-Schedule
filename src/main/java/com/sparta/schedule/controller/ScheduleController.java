package com.sparta.schedule.controller;

import com.sparta.schedule.dto.PasswordDTO;
import com.sparta.schedule.dto.ScheduleRequestDTO;
import com.sparta.schedule.dto.ScheduleResponseDTO;
import com.sparta.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ScheduleResponseDTO> saveSchedule(@RequestBody ScheduleRequestDTO requestDTO) {
        ScheduleResponseDTO responseDTO = scheduleService.saveSchedule(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        List<ScheduleResponseDTO> scheduleList = scheduleService.getAllSchedules();
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<ScheduleResponseDTO> getSchedule(@PathVariable Long id) {
        ScheduleResponseDTO responseDTO = scheduleService.getSchedule(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/schedule/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDTO requestDTO){
        ScheduleResponseDTO responseDTO = scheduleService.updateSchedule(id, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<Long> deleteSchedule(@PathVariable Long id, @RequestBody PasswordDTO passwordDTO) {
        Long deleteSchedule = scheduleService.deleteSchedule(id, passwordDTO.getPassword());
        return new ResponseEntity<>(deleteSchedule, HttpStatus.OK);
    }
}
