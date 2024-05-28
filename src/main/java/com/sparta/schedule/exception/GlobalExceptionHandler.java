package com.sparta.schedule.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j(topic = "exception")
public class GlobalExceptionHandler {

	@ExceptionHandler(ScheduleNotFoundException.class)
	public ResponseEntity<String> handleScheduleNotFoundException(ScheduleNotFoundException e) {
		log.error("excpetion = {}, message = {}", e.getClass(), e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error("excpetion = {}, message = {}", e.getClass(), e.getMessage());

		Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
			.collect(Collectors.toMap(
				error -> error.getField(),
				error -> error.getDefaultMessage(),
				(existingValue, newValue) -> existingValue
			));

		ObjectMapper mapper = new ObjectMapper();
		String response = "";
		try {
			response = mapper.writeValueAsString(errors);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FileUploadException.class)
	public ResponseEntity<String> handleFileUploadException(FileUploadException e) {
		log.error("excpetion = {}, message = {}", e.getClass(), e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<String> handleUserException(UserException e) {
		log.error("excpetion = {}, message = {}", e.getClass(), e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<String> handleLoginException(LoginException e) {
		log.error("excpetion = {}, message = {}", e.getClass(), e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(TokenException.class)
	public ResponseEntity<String> handleJwtException(TokenException e) {
		log.error("excpetion = {}, message = {}", e.getClass(), e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(CommentNotFoundException.class)
	public ResponseEntity<String> handleCommentNotFoundException(CommentNotFoundException e) {
		log.error("excpetion = {}, message = {}", e.getClass(), e.getMessage());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
}
