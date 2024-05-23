package com.sparta.schedule.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.schedule.dto.FileDTO;
import com.sparta.schedule.dto.ResponseMessage;
import com.sparta.schedule.exception.FileUploadException;
import com.sparta.schedule.service.FileUploadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "File Upload", description = "File Upload API")
public class FileUploadController {
	private final FileUploadService fileUploadService;

	@PostMapping("/files")
	@Operation(summary = "File upload", description = "파일을 업로드합니다.")
	public ResponseEntity<ResponseMessage<List<FileDTO>>> uploadFiles(
		@RequestParam("files") List<MultipartFile> files,
		@RequestParam("description") String description) throws FileUploadException {
		List<FileDTO> responseList = fileUploadService.uploadFiles(files, description);

		ResponseMessage<List<FileDTO>> responseMessage = ResponseMessage.<List<FileDTO>>builder()
			.statusCode(HttpStatus.OK.value())
			.message("파일 업로드가 완료되었습니다.")
			.data(responseList)
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
}
