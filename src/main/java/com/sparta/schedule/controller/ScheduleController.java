package com.sparta.schedule.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.schedule.dto.PasswordDTO;
import com.sparta.schedule.dto.ScheduleRequestDTO;
import com.sparta.schedule.dto.ScheduleResponseDTO;
import com.sparta.schedule.service.ScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/schedule")
@Tag(name = "Schedule", description = "Schedule API")
public class ScheduleController {
	private final ScheduleService scheduleService;

	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@PostMapping
	@Operation(summary = "Post schedule", description = "일정을 추가합니다.")
	public ResponseEntity<ScheduleResponseDTO> saveSchedule(@Valid @RequestBody ScheduleRequestDTO requestDTO) {
		ScheduleResponseDTO responseDTO = scheduleService.saveSchedule(requestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get all schedules", description = "모든 일정을 조회합니다.")
	public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
		List<ScheduleResponseDTO> scheduleList = scheduleService.getAllSchedules();
		return new ResponseEntity<>(scheduleList, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get schedule", description = "선택한 일정을 조회합니다.")
	public ResponseEntity<ScheduleResponseDTO> getSchedule(@PathVariable Long id) {
		ScheduleResponseDTO responseDTO = scheduleService.getSchedule(id);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update schedule", description = "선택한 일정을 수정합니다.")
	public ResponseEntity<ScheduleResponseDTO> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDTO requestDTO) {
		ScheduleResponseDTO responseDTO = scheduleService.updateSchedule(id, requestDTO);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete schedule", description = "선택한 일정을 삭제합니다.")
	public ResponseEntity<Long> deleteSchedule(@PathVariable Long id, @Valid @RequestBody PasswordDTO passwordDTO) {
		Long deleteSchedule = scheduleService.deleteSchedule(id, passwordDTO.getPassword());
		return new ResponseEntity<>(deleteSchedule, HttpStatus.OK);
	}
}
