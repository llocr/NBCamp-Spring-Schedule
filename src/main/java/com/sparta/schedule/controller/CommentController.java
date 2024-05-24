package com.sparta.schedule.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.schedule.dto.CommentRequestDTO;
import com.sparta.schedule.dto.CommentResponseDTO;
import com.sparta.schedule.dto.ResponseMessage;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules/{scheduleId}/comments")
public class CommentController {
	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<ResponseMessage<CommentResponseDTO>> createComment(@PathVariable Long scheduleId,
		@Valid @RequestBody CommentRequestDTO requestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentResponseDTO responseDTO = commentService.createComment(scheduleId, requestDTO, userDetails.getUser());

		ResponseMessage<CommentResponseDTO> responseMessage = ResponseMessage.<CommentResponseDTO>builder()
			.statusCode(HttpStatus.CREATED.value())
			.message("댓글이 추가되었습니다.")
			.data(responseDTO)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
	}
}
