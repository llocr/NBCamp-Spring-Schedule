package com.sparta.schedule.controller;

import com.sparta.schedule.dto.PasswordDTO;
import com.sparta.schedule.dto.ScheduleRequestDTO;
import com.sparta.schedule.dto.ScheduleResponseDTO;
import com.sparta.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Schedule", description = "Schedule API")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedule")
    @Operation(summary = "Post schedule", description = "일정을 추가합니다.")
    public ResponseEntity<ScheduleResponseDTO> saveSchedule(@Valid @RequestBody ScheduleRequestDTO requestDTO) {
        ScheduleResponseDTO responseDTO = scheduleService.saveSchedule(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/schedule")
    @Operation(summary = "Get all schedules", description = "모든 일정을 조회합니다.")
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        List<ScheduleResponseDTO> scheduleList = scheduleService.getAllSchedules();
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }

    @GetMapping("/schedule/{id}")
    @Operation(summary = "Get schedule", description = "선택한 일정을 조회합니다.")
    public ResponseEntity<ScheduleResponseDTO> getSchedule(@PathVariable Long id) {
        ScheduleResponseDTO responseDTO = scheduleService.getSchedule(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/schedule/{id}")
    @Operation(summary = "Update schedule", description = "선택한 일정을 수정합니다.")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDTO requestDTO){
        ScheduleResponseDTO responseDTO = scheduleService.updateSchedule(id, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/schedule/{id}")
    @Operation(summary = "Delete schedule", description = "선택한 일정을 삭제합니다.")
    public ResponseEntity<Long> deleteSchedule(@PathVariable Long id,@Valid @RequestBody PasswordDTO passwordDTO) {
        Long deleteSchedule = scheduleService.deleteSchedule(id, passwordDTO.getPassword());
        return new ResponseEntity<>(deleteSchedule, HttpStatus.OK);
    }
}
