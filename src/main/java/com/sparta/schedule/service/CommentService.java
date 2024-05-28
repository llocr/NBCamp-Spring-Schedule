package com.sparta.schedule.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.schedule.dto.CommentRequestDTO;
import com.sparta.schedule.dto.CommentResponseDTO;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.exception.CommentNotFoundException;
import com.sparta.schedule.exception.ScheduleNotFoundException;
import com.sparta.schedule.exception.UserException;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final ScheduleRepository scheduleRepository;

	/*
	1. 댓글 추가
	 */
	@Transactional
	public CommentResponseDTO createComment(Long scheduleId, CommentRequestDTO requestDTO, User user) {
		//스케줄 존재하는지 확인
		Schedule schedule = findScheduleById(scheduleId);

		Comment comment = requestDTO.toEntity(requestDTO, user, schedule);
		commentRepository.save(comment);

		log.info("commentId = {}, message = {}", comment.getId(), "댓글이 추가되었습니다.");

		return new CommentResponseDTO(comment);
	}

	/*
	2. 댓글 조회
	 */
	public CommentResponseDTO getComment(Long scheduleId, Long commentId) {
		//스케줄 존재하는지 확인
		findScheduleById(scheduleId);

		//댓글 존재하는지 확인
		Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

		log.info("commentId = {}, message = {}", comment.getId(), "댓글 조회가 완료되었습니다.");

		return new CommentResponseDTO(comment);
	}

	/*
	3. 해당 게시글 전체 댓글 조회
	 */
	public List<CommentResponseDTO> getAllComments(Long scheduleId) {
		//스케줄 존재하는지 확인
		findScheduleById(scheduleId);

		List<Comment> commentList = commentRepository.findAllByScheduleId(scheduleId);
		if (!commentList.isEmpty()) {
			log.info("scheduleId = {}, message = {}", scheduleId, "댓글 조회가 완료되었습니다.");
			return commentList.stream().map(CommentResponseDTO::new).toList();
		} else {
			throw new CommentNotFoundException();
		}
	}

	/*
	4. 댓글 수정
	 */
	@Transactional
	public CommentResponseDTO updateComment(Long scheduleId, Long commentId, CommentRequestDTO requestDTO, User user) {
		//스케줄 존재하는지 확인
		Schedule schedule = findScheduleById(scheduleId);

		//댓글 존재하는지 & 댓글 작성자인지 확인
		Comment comment = findCommentById(commentId, user.getId());
		comment.update(requestDTO);

		log.info("commentId = {}, message = {}", comment.getId(), "댓글이 수정되었습니다.");

		return new CommentResponseDTO(comment);
	}

	/*
	5. 댓글 삭제
	 */
	@Transactional
	public Long deleteComment(Long scheduleId, Long commentId, User user) {
		//스케줄 존재하는지 확인
		findScheduleById(scheduleId);

		//댓글 존재하는지 & 댓글 작성자인지 확인
		Comment comment = findCommentById(commentId, user.getId());
		commentRepository.delete(comment);

		log.info("commentId = {}, message = {}", comment.getId(), "댓글이 삭제되었습니다.");

		return commentId;
	}

	/*
	아이디로 스케줄 찾기
	 */
	private Schedule findScheduleById(Long scheduleId) {
		Optional<Schedule> findSchedule = scheduleRepository.findById(scheduleId);
		if (findSchedule.isPresent()) {
			return findSchedule.get();
		} else {
			throw new ScheduleNotFoundException();
		}
	}

	/*
	아이디로 댓글 & 댓글 작성자 찾기
	 */
	private Comment findCommentById(Long commentId, Long userID) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
		if (comment.getUser().getId().equals(userID)) {
			return comment;
		} else {
			throw new UserException("댓글 작성자가 아닙니다.");
		}
	}

}
