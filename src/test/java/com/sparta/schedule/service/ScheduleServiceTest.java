package com.sparta.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.schedule.dto.ScheduleRequestDTO;
import com.sparta.schedule.dto.ScheduleResponseDTO;
import com.sparta.schedule.entity.UploadFile;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.exception.ScheduleNotFoundException;
import com.sparta.schedule.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
class ScheduleServiceTest {
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private UserRepository userRepository;
	
	void 스케줄저장() {
		ScheduleRequestDTO requestDTO = new ScheduleRequestDTO();
		requestDTO.setTitle("테스트입니다.");
		requestDTO.setContents("오늘의 스케줄은 없습니다.");

		User user1 = userRepository.findById(2L).orElseThrow();
		User user2 = userRepository.findById(3L).orElseThrow();

		UploadFile file1 = new UploadFile();
		file1.setName("test");
		file1.setExtension("image/png");
		file1.setSize(5);
		file1.setContent(new byte[5]);

		UploadFile file2 = new UploadFile();
		file2.setName("test2");
		file2.setExtension("image/png");
		file2.setSize(5);
		file2.setContent(new byte[5]);

		scheduleService.saveSchedule(requestDTO, user1, file1);
		scheduleService.saveSchedule(requestDTO, user1, null);
		scheduleService.saveSchedule(requestDTO, user1, null);
		scheduleService.saveSchedule(requestDTO, user2, file2);
		scheduleService.saveSchedule(requestDTO, user2, null);
		scheduleService.saveSchedule(requestDTO, user2, null);
	}

	@Test
	@Transactional
	@DisplayName("스케줄 저장 성공 테스트")
	void 스케줄저장성공() {
		//given
		ScheduleRequestDTO requestDTO = new ScheduleRequestDTO();
		requestDTO.setTitle("테스트입니다.");
		requestDTO.setContents("오늘의 스케줄은 없습니다.");

		User user = userRepository.findById(2L).orElseThrow();

		UploadFile file = new UploadFile();
		file.setName("test");
		file.setExtension("image/png");
		file.setSize(5);
		file.setContent(new byte[5]);

		//when
		ScheduleResponseDTO responseDTO = scheduleService.saveSchedule(requestDTO, user, file);

		//then
		assertThat(requestDTO.getTitle()).isEqualTo(responseDTO.getTitle());
		assertThat(user.getUsername()).isEqualTo(responseDTO.getUsername());
	}

	@Test
	@DisplayName("선택된 스케줄 조회 성공 테스트")
	void 선택된스케줄조회성공() {
		//given
		Long id = 2L;

		//when
		ScheduleResponseDTO responseDTO = scheduleService.getSchedule(id);

		//then
		assertThat(id).isEqualTo(responseDTO.getId());
	}

	@Test
	@DisplayName("선택된 스케줄 조회 실패 테스트")
	void 선택된스케줄조회실패() {
		//given
		Long id = 0L;

		//when
		assertThatException().isThrownBy(() -> scheduleService.getSchedule(id));
	}

	@Test
	@DisplayName("등록된 전체 스케줄 조회 테스트")
	void 전체스케줄조회테스트() {
		//when
		List<ScheduleResponseDTO> scheduleList = scheduleService.getAllSchedules();

		//then
		assertThat(scheduleList).isNotEmpty();
	}

	@Test
	@Transactional
	@DisplayName("스케줄 수정 테스트")
	void 스케줄수정성공테스트() {
		//given
		Long id = 2L;
		ScheduleRequestDTO requestDTO = new ScheduleRequestDTO();
		requestDTO.setTitle("수정된 테스트입니다.");
		requestDTO.setContents("오늘의 스케줄은 무엇일까요?");

		User user = userRepository.findById(2L).orElseThrow();

		UploadFile file = new UploadFile();
		file.setName("test");
		file.setExtension("image/png");
		file.setSize(5);
		file.setContent(new byte[5]);

		//when
		ScheduleResponseDTO responseDTO = scheduleService.updateSchedule(id, requestDTO, user, file);

		//then
		assertThat(requestDTO.getTitle()).isEqualTo(responseDTO.getTitle());
		assertThat(user.getUsername()).isEqualTo(responseDTO.getUsername());
	}

	@Test
	@Transactional
	@DisplayName("스케줄 수정 실패 테스트_작성자 불일치")
	void 스케줄수정실패테스트_작성자불일치() {
		//given
		Long id = 2L;
		ScheduleRequestDTO requestDTO = new ScheduleRequestDTO();
		requestDTO.setTitle("수정된 테스트입니다.");
		requestDTO.setContents("오늘의 스케줄은 무엇일까요?");

		User user = userRepository.findById(3L).orElseThrow();

		//when & then
		assertThatException().isThrownBy(() -> scheduleService.updateSchedule(id, requestDTO, user, null));
	}

	@Test
	@Transactional
	@DisplayName("스케줄 수정 실패 테스트_해당스케줄없음")
	void 스케줄수정실패테스트_해당스케줄없음() {
		//given
		Long id = 0L;
		ScheduleRequestDTO requestDTO = new ScheduleRequestDTO();
		requestDTO.setTitle("수정된 테스트입니다.");
		requestDTO.setContents("오늘의 스케줄은 무엇일까요?");

		User user = userRepository.findById(2L).orElseThrow();

		//when
		assertThatException().isThrownBy(() -> scheduleService.updateSchedule(id, requestDTO, user, null));
	}

	@Test
	@Transactional
	@DisplayName("스케줄 삭제 성공 테스트")
	void 스케줄삭제성공테스트() {
		//given
		Long id = 2L;

		User user = userRepository.findById(2L).orElseThrow();

		//when
		Long responseData = scheduleService.deleteSchedule(id, user);

		//then
		assertThat(id).isEqualTo(responseData);
	}

	@Test
	@Transactional
	@DisplayName("스케줄 삭제 실패 테스트_작성자 불일치")
	void 스케줄삭제실패테스트_작성자불일치() {
		//given
		Long id = 2L;

		User user = userRepository.findById(3L).orElseThrow();

		//when
		assertThatException().isThrownBy(() -> scheduleService.deleteSchedule(id, user));
	}

	@Test
	@Transactional
	@DisplayName("스케줄 삭제 실패 테스트_해당스케줄없음")
	void 스케줄삭제실패테스트_해당스케줄없음() {
		//given
		Long id = 0L;
		User user = userRepository.findById(2L).orElseThrow();

		//when
		ScheduleNotFoundException scheduleNotFoundException =
			assertThrows(ScheduleNotFoundException.class, () -> scheduleService.deleteSchedule(id, user));

		//then
		assertThatException().isThrownBy(() -> scheduleService.deleteSchedule(id, user));
	}
}