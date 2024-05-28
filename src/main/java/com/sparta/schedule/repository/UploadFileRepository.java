package com.sparta.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.schedule.entity.UploadFile;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}
