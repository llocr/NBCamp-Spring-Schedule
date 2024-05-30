package com.sparta.schedule.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.schedule.dto.LoginRequestDTO;
import com.sparta.schedule.entity.LoginType;
import com.sparta.schedule.service.LoginHistoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j(topic = "LoginHistoryAspect")
public class LoginHistoryAspect {
	private final LoginHistoryService loginHistoryService;

	@Before("execution(* com.sparta.schedule.service.UserService.login(..)) && args(request, ..)")
	public void logBeforeLoginAttempt(LoginRequestDTO request) {
		String username = request.getUsername();
		log.info("로그인 시도: {}", username);
	}

	@AfterReturning("execution(* com.sparta.schedule.service.UserService.login(..)) && args(request, ..)")
	public void logAfterLoginSuccess(LoginRequestDTO request) {
		String username = request.getUsername();

		loginHistoryService.changeLoginHistory(username, LoginType.AFTER_RETURNING);

		log.info("로그인 성공: {}", username);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@AfterThrowing(pointcut = "execution(* com.sparta.schedule.service.UserService.login(..)) && args(request, ..)", throwing = "ex")
	public void logAfterLoginFailure(LoginRequestDTO request, Exception ex) {
		String username = request.getUsername();

		loginHistoryService.changeLoginHistory(username, LoginType.AFTER_THROWING);

		log.info("로그인 실패: {}", username);
	}
}
