package com.sparta.schedule.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.schedule.entity.LoginHistory;
import com.sparta.schedule.entity.LoginType;
import com.sparta.schedule.exception.UserException;
import com.sparta.schedule.repository.LoginHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "LoginHistoryService")
public class LoginHistoryService {
	private final LoginHistoryRepository loginHistoryRepository;

	/*
	로그인 기록 변경
	 */
	@Transactional
	public void changeLoginHistory(String username, LoginType loginType) {
		LoginHistory loginHistory = findLoginHistory(username);
		loginHistory.setLoginType(loginType);
		log.info("로그인 기록 변경: username = {}, loginType = {}", loginHistory.getUsername(), loginHistory.getLoginType());
	}

	/*
	로그인 기록 객체 찾기
	 */
	private LoginHistory findLoginHistory(String username) {
		Optional<LoginHistory> loginHistory = loginHistoryRepository.findByUsername(username);

		if (loginHistory.isPresent()) {
			return loginHistory.get();
		} else {
			log.error("username = {}, LoginHistory 기록을 찾을 수 없습니다.", username);
			throw new UserException("LoginHistory 기록을 찾을 수 없습니다.");
		}
	}
}
