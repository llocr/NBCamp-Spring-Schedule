package com.sparta.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.schedule.dto.UserRequestDTO;
import com.sparta.schedule.dto.UserResponseDTO;

@SpringBootTest
class UserServiceTest {
	@Autowired
	UserService userService;

	@Test
	@Transactional
	@DisplayName("유저 생성 테스트")
	void 유저생성테스트() {
		//given
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUsername("heehee");
		userRequestDTO.setPassword("1234hello");
		userRequestDTO.setUserRole("ROLE_USER");

		//when
		UserResponseDTO user = userService.createUser(userRequestDTO);

		//then
		assertThat(user.getUsername()).isEqualTo(userRequestDTO.getUsername());
	}

}