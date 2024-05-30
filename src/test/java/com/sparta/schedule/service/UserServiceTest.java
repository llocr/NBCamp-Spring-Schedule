package com.sparta.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.schedule.dto.LoginRequestDTO;
import com.sparta.schedule.dto.LoginResponseDTO;
import com.sparta.schedule.dto.UserRequestDTO;
import com.sparta.schedule.dto.UserResponseDTO;
import com.sparta.schedule.exception.LoginException;
import com.sparta.schedule.exception.UserException;
import com.sparta.schedule.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
	@Autowired
	UserService userService;
	@Autowired
	private UserRepository userRepository;

	void createUsers() {
		UserRequestDTO user1 = new UserRequestDTO();
		user1.setUsername("test");
		user1.setPassword("1234");
		user1.setUserRole("ROLE_USER");
		userService.createUser(user1);

		UserRequestDTO user2 = new UserRequestDTO();
		user2.setUsername("heesue");
		user2.setPassword("1234");
		user2.setUserRole("ROLE_USER");
		userService.createUser(user2);

		UserRequestDTO user3 = new UserRequestDTO();
		user3.setUsername("hello");
		user3.setPassword("1234");
		user3.setUserRole("ROLE_USER");
		userService.createUser(user3);
	}

	@Test
	@Transactional
	@DisplayName("유저 생성 성공 테스트")
	void 유저생성성공() {
		//given
		UserRequestDTO requestDTO = new UserRequestDTO();
		requestDTO.setUsername("hihello");
		requestDTO.setPassword("1234");
		requestDTO.setUserRole("ROLE_USER");

		//when
		UserResponseDTO userResponseDTO = userService.createUser(requestDTO);

		//then
		assertThat(requestDTO.getUsername()).isEqualTo(userResponseDTO.getUsername());
	}

	@Test
	@DisplayName("유저 생성 실패 테스트 - 동명이인")
	void 유저생성실패_동명이인() {
		//given
		UserRequestDTO requestDTO = new UserRequestDTO();
		requestDTO.setUsername("test"); // 이미 등록된 유저 이름
		requestDTO.setPassword("1234");
		requestDTO.setUserRole("ROLE_USER");

		//when & then
		assertThrows(UserException.class, () -> userService.createUser(requestDTO));
	}

	@Test
	@DisplayName("유저 생성 실패 테스트 - 권한 유효성 검사")
	void 유저생성실패_권한유효성검사() {
		//given
		UserRequestDTO requestDTO = new UserRequestDTO();
		requestDTO.setUsername("test1234"); // 이미 등록된 유저 이름
		requestDTO.setPassword("1234");
		requestDTO.setUserRole("USER");

		//when & then
		assertThrows(UserException.class, () -> userService.createUser(requestDTO));
	}

	@Test
	@DisplayName("유저 로그인 성공 테스트")
	void 유저로그인성공테스트() {
		//given
		LoginRequestDTO requestDTO = new LoginRequestDTO();
		requestDTO.setUsername("test");
		requestDTO.setPassword("1234");

		//when
		LoginResponseDTO responseDTO = userService.login(requestDTO);

		//then
		assertThat(responseDTO.getUsername()).isEqualTo(requestDTO.getUsername());
	}

	@Test
	@DisplayName("유저 로그인 실패 테스트 - 존재하지 않는 유저")
	void 유저로그인실패테스트_존재하지않는유저() {
		//given
		LoginRequestDTO requestDTO = new LoginRequestDTO();
		requestDTO.setUsername("kekekeeekee");
		requestDTO.setPassword("1234");

		//when
		assertThrows(UserException.class, () -> userService.login(requestDTO));
	}

	@Test
	@DisplayName("유저 로그인 실패 테스트 - 비밀번호 불일치")
	void 유저로그인실패테스트_비밀번호불일치() {
		//given
		LoginRequestDTO requestDTO = new LoginRequestDTO();
		requestDTO.setUsername("test");
		requestDTO.setPassword("12345"); // 틀린 비밀번호

		//when
		assertThrows(LoginException.class, () -> userService.login(requestDTO));
	}

}