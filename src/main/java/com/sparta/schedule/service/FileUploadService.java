package com.sparta.schedule.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.schedule.entity.UploadFile;
import com.sparta.schedule.exception.FileUploadException;
import com.sparta.schedule.repository.UploadFileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileUploadService {

	private final UploadFileRepository fileRepository;

	@Transactional
	public UploadFile storeFile(MultipartFile file) {
		String extension = validateImage(file);
		int size = validateSize(file);
		UploadFile uploadFile = new UploadFile();

		uploadFile.setName(file.getOriginalFilename());
		uploadFile.setExtension(extension);
		uploadFile.setSize(size);

		try {
			uploadFile.setContent(file.getBytes());
		} catch (IOException e) {
			throw new FileUploadException("파일을 저장하지 못했습니다.");
		}

		return uploadFile;
	}

	/*
	이미지 파일인지 확인
	 */
	private String validateImage(MultipartFile file) throws FileUploadException {
		String contentType = file.getContentType();
		if (!("image/png").equals(contentType) && !("image/jpeg").equals(contentType)) {
			throw new FileUploadException("파일 형식은 png, jpg만 가능합니다.");
		}
		return contentType;
	}

	/*
	파일 용량 확인
	 */
	private int validateSize(MultipartFile file) {
		if (file.getSize() > 5 * 1024 * 1024) {
			throw new FileUploadException("파일 용량은 최대 5MB까지만 가능합니다.");
		}
		return (int)file.getSize();
	}
}
