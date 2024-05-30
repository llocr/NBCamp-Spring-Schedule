package com.sparta.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.schedule.dto.CommentRequestDTO;
import com.sparta.schedule.dto.CommentResponseDTO;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
class CommentServiceTest {
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserRepository userRepository;

	void 댓글저장() {
		CommentRequestDTO requestDTO = new CommentRequestDTO();
		requestDTO.setContents("댓글입니다.");

		Long scheduleId1 = 2L;
		Long scheduleId2 = 3L;
		User user1 = userRepository.findById(4L).orElseThrow();
		User user2 = userRepository.findById(3L).orElseThrow();

		commentService.createComment(scheduleId1, requestDTO, user1);
		commentService.createComment(scheduleId1, requestDTO, user1);
		commentService.createComment(scheduleId1, requestDTO, user1);
		commentService.createComment(scheduleId2, requestDTO, user2);
		commentService.createComment(scheduleId2, requestDTO, user2);
		commentService.createComment(scheduleId2, requestDTO, user2);
		commentService.createComment(scheduleId2, requestDTO, user2);
	}

	@Test
	@Transactional
	@DisplayName("댓글 저장 성공 테스트")
	void 댓글저장성공테스트() {
		//given
		CommentRequestDTO requestDTO = new CommentRequestDTO();
		requestDTO.setContents("댓글입니다.");

		Long scheduleId = 2L;
		User user = userRepository.findById(4L).orElseThrow();

		//when
		CommentResponseDTO responseDTO = commentService.createComment(scheduleId, requestDTO, user);

		//then
		assertThat(responseDTO.getContents()).isEqualTo(requestDTO.getContents());
		assertThat(responseDTO.getUsername()).isEqualTo(user.getUsername());
	}

	@Test
	@Transactional
	@DisplayName("댓글 저장 실패 테스트")
	void 댓글저장실패테스트_게시물없음() {
		//given
		CommentRequestDTO requestDTO = new CommentRequestDTO();
		requestDTO.setContents("댓글입니다.");

		Long scheduleId = 0L;
		User user = userRepository.findById(4L).orElseThrow();

		//when & then
		assertThatException().isThrownBy(() -> commentService.createComment(scheduleId, requestDTO, user));
	}

	@Test
	@DisplayName("단일 댓글 조회 성공 테스트")
	void 단일댓글조회성공테스트() {
		//given
		Long ScheduleId = 2L;
		Long commentId = 1L;

		//when
		CommentResponseDTO responseDTO = commentService.getComment(ScheduleId, commentId);

		//then
		assertThat(responseDTO.getId()).isEqualTo(commentId);
	}

	@Test
	@DisplayName("단일 댓글 조회 실패 테스트_게시물없음")
	void 단일댓글조회실패테스트_게시물없음() {
		//given
		Long ScheduleId = 0L;
		Long commentId = 1L;

		//when & then
		assertThatException().isThrownBy(() -> commentService.getComment(ScheduleId, commentId));
	}

	@Test
	@DisplayName("단일 댓글 조회 실패 테스트_댓글없음")
	void 단일댓글조회실패테스트_댓글없음() {
		//given
		Long ScheduleId = 2L;
		Long commentId = 0L;

		//when & then
		assertThatException().isThrownBy(() -> commentService.getComment(ScheduleId, commentId));
	}

	@Test
	@DisplayName("단일 댓글 조회 실패 테스트_댓글이 다른 게시물에 존재")
	void 단일댓글조회실패테스트_댓글이다른게시물에존재() {
		//given
		Long ScheduleId = 2L;
		Long commentId = 4L;

		//when & then
		assertThatException().isThrownBy(() -> commentService.getComment(ScheduleId, commentId));
	}

	@Test
	@DisplayName("전체 댓글 조회 성공 테스트")
	void 전체댓글조회성공테스트() {
		//given
		Long scheduleId = 2L;

		//when
		commentService.getAllComments(scheduleId);

		//then
		assertThat(commentService.getAllComments(scheduleId)).isNotNull();
	}

	@Test
	@DisplayName("전체 댓글 조회 실패 테스트")
	void 전체댓글조회실패테스트_게시물없음() {
		//given
		Long scheduleId = 0L;

		//when & then
		assertThatException().isThrownBy(() -> commentService.getAllComments(scheduleId));
	}

	@Test
	@Transactional
	@DisplayName("댓글 수정 성공 테스트")
	void 댓글수정성공테스트() {
		//given
		CommentRequestDTO requestDTO = new CommentRequestDTO();
		requestDTO.setContents("수정된 테스트입니다");

		Long scheduleId = 2L;
		Long commentId = 1L;
		User user = userRepository.findById(4L).orElseThrow();

		//when
		CommentResponseDTO responseDTO = commentService.updateComment(scheduleId, commentId, requestDTO, user);

		//then
		assertThat(responseDTO.getContents()).isEqualTo(requestDTO.getContents());
		assertThat(responseDTO.getUsername()).isEqualTo(user.getUsername());
	}

	@Test
	@Transactional
	@DisplayName("댓글 수정 실패 테스트_게시물 없음")
	void 댓글수정실패테스트_게시물없음() {
		//given
		CommentRequestDTO requestDTO = new CommentRequestDTO();
		requestDTO.setContents("수정된 테스트입니다");

		Long scheduleId = 0L;
		Long commentId = 1L;
		User user = userRepository.findById(4L).orElseThrow();

		//when & then
		assertThatException().isThrownBy(() -> commentService.updateComment(scheduleId, commentId, requestDTO, user));
	}

	@Test
	@Transactional
	@DisplayName("댓글 수정 실패 테스트_댓글 없음")
	void 댓글수정실패테스트_댓글없음() {
		//given
		CommentRequestDTO requestDTO = new CommentRequestDTO();
		requestDTO.setContents("수정된 테스트입니다");

		Long scheduleId = 2L;
		Long commentId = 0L;
		User user = userRepository.findById(4L).orElseThrow();

		//when & then
		assertThatException().isThrownBy(() -> commentService.updateComment(scheduleId, commentId, requestDTO, user));
	}

	@Test
	@Transactional
	@DisplayName("댓글 수정 실패 테스트_작성자불일치")
	void 댓글수정실패테스트_작성자불일치() {
		//given
		CommentRequestDTO requestDTO = new CommentRequestDTO();
		requestDTO.setContents("수정된 테스트입니다");

		Long scheduleId = 2L;
		Long commentId = 1L;
		User user = userRepository.findById(3L).orElseThrow();

		//when & then
		assertThatException().isThrownBy(() -> commentService.updateComment(scheduleId, commentId, requestDTO, user));
	}

	@Test
	@Transactional
	@DisplayName("댓글 수정 실패 테스트_댓글이 다른 게시물에 존재")
	void 댓글수정실패테스트_댓글이다른게시물에존재() {
		//given
		CommentRequestDTO requestDTO = new CommentRequestDTO();
		requestDTO.setContents("수정된 테스트입니다");

		Long scheduleId = 2L;
		Long commentId = 5L;
		User user = userRepository.findById(4L).orElseThrow();

		//when & then
		assertThatException().isThrownBy(() -> commentService.updateComment(scheduleId, commentId, requestDTO, user));
	}

	@Test
	@Transactional
	@DisplayName("댓글 삭제 성공 테스트")
	void 댓글삭제성공테스트() {
		//given
		Long scheduleId = 2L;
		Long commentId = 1L;

		User user = userRepository.findById(4L).orElseThrow();

		//when
		Long responseData = commentService.deleteComment(scheduleId, commentId, user);

		//then
		assertThat(responseData).isEqualTo(commentId);
	}

	@Test
	@DisplayName("댓글 삭제 실패 테스트_게시물 없음")
	void 댓글삭제실패테스트_해당게시물없음() {
		//given
		Long scheduleId = 0L;
		Long commentId = 1L;

		User user = userRepository.findById(4L).orElseThrow();

		//when & then
		assertThatException().isThrownBy(() -> commentService.deleteComment(scheduleId, commentId, user));
	}

	@Test
	@DisplayName("댓글 삭제 실패 테스트_댓글 없음")
	void 댓글삭제실패테스트_해당댓글없음() {
		//given
		Long scheduleId = 2L;
		Long commentId = 0L;

		User user = userRepository.findById(4L).orElseThrow();

		//when & then
		assertThatException().isThrownBy(() -> commentService.deleteComment(scheduleId, commentId, user));
	}

	@Test
	@DisplayName("댓글 삭제 실패 테스트_작성자 불일치")
	void 댓글삭제실패테스트_작성자불일치() {
		//given
		Long scheduleId = 2L;
		Long commentId = 1L;

		User user = userRepository.findById(2L).orElseThrow();

		//when & then
		assertThatException().isThrownBy(() -> commentService.deleteComment(scheduleId, commentId, user));
	}

	@Test
	@DisplayName("댓글 삭제 실패 테스트_댓글이 다른 게시물에 존재")
	void 댓글삭제실패테스트_댓글이다른게시물에존재() {
		//given
		Long scheduleId = 2L;
		Long commentId = 4L;

		User user = userRepository.findById(3L).orElseThrow();

		//when & then
		assertThatException().isThrownBy(() -> commentService.deleteComment(scheduleId, commentId, user));
	}
}