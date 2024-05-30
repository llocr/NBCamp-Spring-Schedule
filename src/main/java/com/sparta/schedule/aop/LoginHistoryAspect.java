package com.sparta.schedule.aop;

import java.util.Optional;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.schedule.dto.LoginRequestDTO;
import com.sparta.schedule.entity.LoginHistory;
import com.sparta.schedule.entity.LoginType;
import com.sparta.schedule.exception.UserException;
import com.sparta.schedule.repository.LoginHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j(topic = "LoginHistoryAspect")
public class LoginHistoryAspect {
	private final LoginHistoryRepository loginHistoryRepository;

	@Before("execution(* com.sparta.schedule.service.UserService.login(..)) && args(request, ..)")
	public void logBeforeLoginAttempt(LoginRequestDTO request) {
		String username = request.getUsername();
		log.info("로그인 시도: {}", username);
	}

	@Transactional
	@AfterReturning("execution(* com.sparta.schedule.service.UserService.login(..)) && args(request, ..)")
	public void logAfterLoginSuccess(LoginRequestDTO request) {
		String username = request.getUsername();

		LoginHistory loginHistory = findLoginHistory(username);
		loginHistory.setLoginType(LoginType.AFTER_RETURNING);
		loginHistoryRepository.save(loginHistory);

		log.info("로그인 성공: {}", username);
	}

	@Transactional
	@AfterThrowing(pointcut = "execution(* com.sparta.schedule.service.UserService.login(..)) && args(request, ..)", throwing = "ex")
	public void logAfterLoginFailure(LoginRequestDTO request, Exception ex) {
		String username = request.getUsername();

		LoginHistory loginHistory = findLoginHistory(username);
		loginHistory.setLoginType(LoginType.AFTER_THROWING);

		loginHistoryRepository.save(loginHistory); //여기서 안 됨

		log.info("로그인 실패: {}", username);
	}

	private LoginHistory findLoginHistory(String username) {
		Optional<LoginHistory> loginHistory = loginHistoryRepository.findByUsername(username);
		log.info("username = {}, LoginHistory 기록을 찾습니다.", username);

		if (loginHistory.isPresent()) {
			log.info("username = {}, LoginHistory 기록을 찾았습니다.", username);
			return loginHistory.get();
		} else {
			log.error("username = {}, LoginHistory 기록을 찾을 수 없습니다.", username);
			throw new UserException("LoginHistory 기록을 찾을 수 없습니다.");
		}
	}
}
