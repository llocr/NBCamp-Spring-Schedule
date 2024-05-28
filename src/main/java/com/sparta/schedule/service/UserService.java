package com.sparta.schedule.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.schedule.dto.UserRequestDTO;
import com.sparta.schedule.dto.UserResponseDTO;
import com.sparta.schedule.entity.User;
import com.sparta.schedule.entity.UserRole;
import com.sparta.schedule.exception.UserException;
import com.sparta.schedule.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j(topic = "UserLog")
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	/*
	1. 회원가입
	 */
	@Transactional
	public UserResponseDTO createUser(UserRequestDTO requestDTO) {
		//이름 유효성 검사
		validateUserName(requestDTO.getUsername());

		//비밀번호 암호화
		String password = passwordEncoder.encode(requestDTO.getPassword());

		//권한 설정
		UserRole userRole = getUserRole(requestDTO);

		//User 객체 생성
		User user = requestDTO.toEntity(password, userRole);

		//DB에 저장
		User saveUser = userRepository.save(user);

		log.info("username = {}, message = {}", saveUser.getUsername(), "회원가입이 완료되었습니다.");
		return new UserResponseDTO(saveUser);
	}

	/*
	권한 설정
	 */
	private static UserRole getUserRole(UserRequestDTO requestDTO) {
		String inputRole = requestDTO.getUserRole();
		UserRole role = null;
		for (UserRole value : UserRole.values()) {
			if (value.name().equals(inputRole)) {
				role = value;
			}
		}

		if (role == null) {
			throw new UserException("권한이 잘못되었습니다.");
		}
		return role;
	}

	/*
	usename 중복 검사
	 */
	private void validateUserName(String username) {
		Optional<User> findUser = userRepository.findByUsername(username);
		if (findUser.isPresent()) {
			throw new UserException("중복된 username 입니다.");
		}
	}
}
