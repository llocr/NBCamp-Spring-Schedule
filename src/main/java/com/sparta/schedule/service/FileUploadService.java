package com.sparta.schedule.service;

import com.sparta.schedule.dto.FileDTO;
import com.sparta.schedule.exception.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    /*
    1. 파일 업로드
     */
    public List<FileDTO> uploadFiles(List<MultipartFile> files, String description) throws FileUploadException {
        if (!files.isEmpty()) {
            List<FileDTO> fileList = new ArrayList<>();
            for (MultipartFile file : files) {
                validateImage(file);
                FileDTO responseDTO = saveFile(file, description);
                fileList.add(responseDTO);
            }
            return fileList;
        } else {
            throw new FileUploadException("파일이 비어있습니다.");
        }
    }

    /*
    이미지 파일인지 확인
     */
    private void validateImage(MultipartFile file) throws FileUploadException {
        String contentType = file.getContentType();
        if(contentType == null || !contentType.startsWith("image/")) {
            throw new FileUploadException("업로드 된 파일이 이미지 형식이 아닙니다.");
        }
    }

    /*
    컴퓨터에 파일 저장
     */
    private FileDTO saveFile(MultipartFile file, String description) throws FileUploadException {
        //파일 저장 경로 설정
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            return new FileDTO(file.getOriginalFilename(), fileName, path.toString(), description);
        } catch (IOException e) {
            throw new FileUploadException("파일 저장에 실패했습니다.");
        }
    }

}
