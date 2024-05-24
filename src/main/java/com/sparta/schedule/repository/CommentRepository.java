package com.sparta.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.schedule.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findAllByScheduleId(Long scheduleId);
}
