package com.sparta.schedule.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.schedule.dto.ScheduleRequestDTO;
import com.sparta.schedule.dto.ScheduleResponseDTO;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.entity.UploadFile;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.exception.ScheduleNotFoundException;
import com.sparta.schedule.exception.TokenException;
import com.sparta.schedule.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {
	private final ScheduleRepository scheduleRepository;

	/*
	1. 일정 추가
	 */
	@Transactional
	public ScheduleResponseDTO saveSchedule(ScheduleRequestDTO requestDTO, User user, UploadFile uploadFile) {
		Schedule schedule = requestDTO.toEntity(user, uploadFile);
		Schedule saveSchedule = scheduleRepository.save(schedule);

		return new ScheduleResponseDTO(saveSchedule);
	}

	/*
	2. 일정 전체 조회
	 */
	public List<ScheduleResponseDTO> getAllSchedules() {
		List<Schedule> scheduleList = scheduleRepository.findAllByOrderByModifyDateDesc();

		if (!scheduleList.isEmpty()) {
			return scheduleList.stream().map(ScheduleResponseDTO::new).toList();
		} else {
			throw new IllegalArgumentException("등록된 스케줄이 없습니다.");
		}
	}

	/*
	3. 선택한 일정 조회
	 */
	public ScheduleResponseDTO getSchedule(Long id) {
		Schedule schedule = findScheduleById(id);
		return new ScheduleResponseDTO(schedule);
	}

	/*
	4. 선택한 일정 수정
	 */
	@Transactional
	public ScheduleResponseDTO updateSchedule(Long id, ScheduleRequestDTO requestDTO, User user, UploadFile file) {
		Schedule schedule = findScheduleById(id);
		validateUser(schedule, user);

		schedule.update(requestDTO, file);
		return new ScheduleResponseDTO(schedule);
	}

	/*
	5. 선택한 일정 삭제
	 */
	@Transactional
	public Long deleteSchedule(Long id, User user) {
		Schedule schedule = findScheduleById(id);
		validateUser(schedule, user);

		scheduleRepository.delete(schedule);
		return schedule.getId();
	}

	/*
	아이디로 Schedule 검색
	 */
	private Schedule findScheduleById(Long id) {
		Optional<Schedule> findSchedule = scheduleRepository.findById(id);

		if (findSchedule.isPresent()) {
			return findSchedule.get();
		} else {
			throw new ScheduleNotFoundException();
		}
	}

	/*
	사용자 확인
	 */
	private void validateUser(Schedule schedule, User user) {
		if (!schedule.getUser().getId().equals(user.getId())) {
			throw new TokenException("작성자만 삭제/수정할 수 있습니다.");
		}
	}
}
