package com.sparta.schedule.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.schedule.dto.LoginRequestDTO;
import com.sparta.schedule.dto.LoginResponseDTO;
import com.sparta.schedule.dto.ResponseMessage;
import com.sparta.schedule.dto.UserRequestDTO;
import com.sparta.schedule.dto.UserResponseDTO;
import com.sparta.schedule.exception.LoginException;
import com.sparta.schedule.jwt.JwtUtil;
import com.sparta.schedule.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j(topic = "UserLog")
@Tag(name = "User", description = "User API")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	@Operation(summary = "Sign up", description = "회원가입을 진행합니다.")
	public ResponseEntity<ResponseMessage<UserResponseDTO>> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
		UserResponseDTO responseDTO = userService.createUser(requestDTO);

		ResponseMessage<UserResponseDTO> responseMessage = ResponseMessage.<UserResponseDTO>builder()
			.statusCode(HttpStatus.CREATED.value())
			.message("회원가입이 완료되었습니다.")
			.data(responseDTO)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseMessage<String>> login(HttpServletResponse response, @Valid @RequestBody LoginRequestDTO requestDTO) {
		try {
			LoginResponseDTO responseDTO = userService.login(requestDTO);

			response.addHeader(JwtUtil.AUTHORIZATION_HEADER, responseDTO.getAccessToken());
			response.addHeader(JwtUtil.REFRESHTOKEN_HEADER, responseDTO.getRefreshToken());

			ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
				.statusCode(HttpStatus.OK.value())
				.message("로그인에 성공했습니다.")
				.data(responseDTO.getUsername())
				.build();

			return new ResponseEntity<>(responseMessage, HttpStatus.OK);
		} catch (LoginException e) {
			log.error("message = {}", "로그인 요청이 비어 있거나 불완전합니다.");
			throw new LoginException("로그인 요청이 비어 있거나 불완전합니다.");
		}
	}
}
