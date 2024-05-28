package com.sparta.schedule.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.schedule.dto.CommentRequestDTO;
import com.sparta.schedule.dto.CommentResponseDTO;
import com.sparta.schedule.dto.ResponseMessage;
import com.sparta.schedule.security.UserDetailsImpl;
import com.sparta.schedule.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules/{scheduleId}/comments")
@Tag(name = "Comment", description = "Comment API")
public class CommentController {
	private final CommentService commentService;

	@PostMapping
	@Operation(summary = "Post comment", description = "댓글을 추가합니다.")
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

	@GetMapping("/{commentId}")
	@Operation(summary = "Get comment", description = "선택한 댓글을 조회합니다.")
	public ResponseEntity<ResponseMessage<CommentResponseDTO>> getComment(@PathVariable Long scheduleId, @PathVariable Long commentId) {
		CommentResponseDTO responseDTO = commentService.getComment(scheduleId, commentId);

		ResponseMessage<CommentResponseDTO> responseMessage = ResponseMessage.<CommentResponseDTO>builder()
			.statusCode(HttpStatus.OK.value())
			.message("댓글 조회가 완료되었습니다.")
			.data(responseDTO)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

	@GetMapping
	@Operation(summary = "Get all comments", description = "해당 게시글은 전체 댓글을 조회합니다.")
	public ResponseEntity<ResponseMessage<List<CommentResponseDTO>>> getAllComments(@PathVariable Long scheduleId) {
		List<CommentResponseDTO> responseDTOList = commentService.getAllComments(scheduleId);

		ResponseMessage<List<CommentResponseDTO>> responseMessage = ResponseMessage.<List<CommentResponseDTO>>builder()
			.statusCode(HttpStatus.OK.value())
			.message("댓글 조회가 완료되었습니다.")
			.data(responseDTOList)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

	@PutMapping("/{commentId}")
	@Operation(summary = "Update comment", description = "선택한 댓글을 수정합니다.")
	public ResponseEntity<ResponseMessage<CommentResponseDTO>> updateComment(@PathVariable Long scheduleId, @PathVariable Long commentId,
		@Valid @RequestBody CommentRequestDTO requestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentResponseDTO responseDTO = commentService.updateComment(scheduleId, commentId, requestDTO, userDetails.getUser());

		ResponseMessage<CommentResponseDTO> responseMessage = ResponseMessage.<CommentResponseDTO>builder()
			.statusCode(HttpStatus.OK.value())
			.message("댓글이 수정되었습니다.")
			.data(responseDTO)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}

	@DeleteMapping("/{commentId}")
	@Operation(summary = "Delete comment", description = "선택한 댓글을 삭제합니다.")
	public ResponseEntity<ResponseMessage<Long>> deleteComment(@PathVariable Long scheduleId, @PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long responseData = commentService.deleteComment(scheduleId, commentId, userDetails.getUser());

		ResponseMessage<Long> responseMessage = ResponseMessage.<Long>builder()
			.statusCode(HttpStatus.OK.value())
			.message("댓글이 삭제되었습니다.")
			.data(responseData)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
}
