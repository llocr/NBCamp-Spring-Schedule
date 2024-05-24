package com.sparta.schedule.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.schedule.dto.ResponseMessage;
import com.sparta.schedule.dto.UserRequestDTO;
import com.sparta.schedule.dto.UserResponseDTO;
import com.sparta.schedule.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<ResponseMessage<UserResponseDTO>> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
		UserResponseDTO responseDTO = userService.createUser(requestDTO);

		ResponseMessage<UserResponseDTO> responseMessage = ResponseMessage.<UserResponseDTO>builder()
			.statusCode(HttpStatus.CREATED.value())
			.message("회원가입이 완료되었습니다.")
			.data(responseDTO)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}
}
