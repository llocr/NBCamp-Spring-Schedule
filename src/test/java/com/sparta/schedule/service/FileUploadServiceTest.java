package com.sparta.schedule.service;

import com.sparta.schedule.dto.FileDTO;
import com.sparta.schedule.exception.FileUploadException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class FileUploadServiceTest {
    @Autowired FileUploadService fileUploadService;

    @Test
    @DisplayName("단일 파일 업로드 테스트")
    void 단일파일업로드테스트() throws FileUploadException {
        // given
        List<MultipartFile> fileList = new ArrayList<>();
        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return "test.jpg";
            }

            @Override
            public String getContentType() {
                return "image/jpeg";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 1024;
            }

            @Override
            public byte[] getBytes() {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
        fileList.add(file);
        String description = "테스트 파일";

        // when
        List<FileDTO> uploadFiles = fileUploadService.uploadFiles(fileList, description);

        // then
        assertThat(uploadFiles.get(0).getOriginalFileName()).isEqualTo("test.jpg");
    }

    @Test
    @DisplayName("다중 파일 업로드 테스트")
    void 다중파일업로드테스트() throws FileUploadException {
        //given
        List<MultipartFile> fileList = new ArrayList<>();
        MultipartFile file1 = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return "test.jpg";
            }

            @Override
            public String getContentType() {
                return "image/jpeg";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 1024;
            }

            @Override
            public byte[] getBytes() {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };

        MultipartFile file2 = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return "test.png";
            }

            @Override
            public String getContentType() {
                return "image/jpeg";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 1024;
            }

            @Override
            public byte[] getBytes() {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };

        fileList.add(file1);
        fileList.add(file2);
        String description = "테스트 파일";

        // when
        List<FileDTO> uploadFiles = fileUploadService.uploadFiles(fileList, description);

        // then
        assertThat(uploadFiles.get(0).getOriginalFileName()).isEqualTo("test.jpg");
        assertThat(uploadFiles.get(1).getOriginalFileName()).isEqualTo("test.png");
    }

    @Test
    @DisplayName("파일 업로드 실패 테스트_파일 없음")
    void 파일업로드실패테스트_파일없음() {
        //given
        List<MultipartFile> fileList = new ArrayList<>();
        String description = "테스트 파일";

        //when
        FileUploadException fileUploadException =
                assertThrows(FileUploadException.class, () -> fileUploadService.uploadFiles(fileList, description));

        //then
        assertThat(fileUploadException.getMessage()).isEqualTo("파일이 비어있습니다.");
    }

    @Test
    @DisplayName("파일 업로드 실패 테스트_이미지 형식 아님")
    void 파일업로드실패테스트_이미지형식아님() {
        //given
        List<MultipartFile> fileList = new ArrayList<>();
        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return "test.txt";
            }

            @Override
            public String getContentType() {
                return "text/plain";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 1024;
            }

            @Override
            public byte[] getBytes() {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
        String description = "테스트 파일";
        fileList.add(file);

        //when
        FileUploadException fileUploadException =
                assertThrows(FileUploadException.class, () -> fileUploadService.uploadFiles(fileList, description));

        //then
        assertThat(fileUploadException.getMessage()).isEqualTo("업로드 된 파일이 이미지 형식이 아닙니다.");
    }
}