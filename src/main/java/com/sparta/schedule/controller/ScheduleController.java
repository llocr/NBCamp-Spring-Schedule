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
import com.sparta.schedule.dto.ResponseMessage;
import com.sparta.schedule.dto.ScheduleRequestDTO;
import com.sparta.schedule.dto.ScheduleResponseDTO;
import com.sparta.schedule.service.ScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@Tag(name = "Schedule", description = "Schedule API")
public class ScheduleController {
	private final ScheduleService scheduleService;

	@PostMapping
	@Operation(summary = "Post schedule", description = "일정을 추가합니다.")
	public ResponseEntity<ResponseMessage<ScheduleResponseDTO>> saveSchedule(@Valid @RequestBody ScheduleRequestDTO requestDTO) {
		ScheduleResponseDTO responseDTO = scheduleService.saveSchedule(requestDTO);

		ResponseMessage<ScheduleResponseDTO> responseMessage = ResponseMessage.<ScheduleResponseDTO>builder()
			.statusCode(HttpStatus.CREATED.value())
			.message("일정이 추가되었습니다.")
			.data(responseDTO)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get all schedules", description = "모든 일정을 조회합니다.")
	public ResponseEntity<ResponseMessage<List<ScheduleResponseDTO>>> getAllSchedules() {
		List<ScheduleResponseDTO> responseLit = scheduleService.getAllSchedules();

		ResponseMessage<List<ScheduleResponseDTO>> responseMessage = ResponseMessage.<List<ScheduleResponseDTO>>builder()
			.statusCode(HttpStatus.OK.value())
			.message("목록 조회가 완료되었습니다.")
			.data(responseLit)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get schedule", description = "선택한 일정을 조회합니다.")
	public ResponseEntity<ResponseMessage<ScheduleResponseDTO>> getSchedule(@PathVariable Long id) {
		ScheduleResponseDTO responseDTO = scheduleService.getSchedule(id);

		ResponseMessage<ScheduleResponseDTO> responseMessage = ResponseMessage.<ScheduleResponseDTO>builder()
			.statusCode(HttpStatus.OK.value())
			.message("선택한 일정 조회가 완료되었습니다.")
			.data(responseDTO)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update schedule", description = "선택한 일정을 수정합니다.")
	public ResponseEntity<ResponseMessage<ScheduleResponseDTO>> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleRequestDTO requestDTO) {
		ScheduleResponseDTO responseDTO = scheduleService.updateSchedule(id, requestDTO);

		ResponseMessage<ScheduleResponseDTO> responseMessage = ResponseMessage.<ScheduleResponseDTO>builder()
			.statusCode(HttpStatus.OK.value())
			.message("선택한 일정 수정이 완료되었습니다.")
			.data(responseDTO)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete schedule", description = "선택한 일정을 삭제합니다.")
	public ResponseEntity<ResponseMessage<Long>> deleteSchedule(@PathVariable Long id, @Valid @RequestBody PasswordDTO passwordDTO) {
		Long responseData = scheduleService.deleteSchedule(id, passwordDTO.getPassword());

		ResponseMessage<Long> responseMessage = ResponseMessage.<Long>builder()
			.statusCode(HttpStatus.OK.value())
			.message("선택한 일정 삭제가 완료되었습니다.")
			.data(responseData)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
}
