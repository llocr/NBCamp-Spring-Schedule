package com.sparta.schedule.controller;

import com.sparta.schedule.dto.FileDTO;
import com.sparta.schedule.exception.FileUploadException;
import com.sparta.schedule.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "File Upload", description = "File Upload API")
public class FileUploadController {
    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/file-upload")
    @Operation(summary = "File upload", description = "파일을 업로드합니다.")
    public ResponseEntity<List<FileDTO>> uploadFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("description") String description) throws FileUploadException {
        List<FileDTO> uploadFiles = fileUploadService.uploadFiles(files, description);

        return new ResponseEntity<>(uploadFiles, HttpStatus.OK);
    }
}
