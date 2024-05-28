package com.sparta.schedule.jwt;

import java.io.EOFException;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule.dto.LoginRequestDTO;
import com.sparta.schedule.dto.ResponseMessage;
import com.sparta.schedule.entity.UserRole;
import com.sparta.schedule.exception.LoginException;
import com.sparta.schedule.security.UserDetailsImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//로그인 및 JWT 생성
@Slf4j(topic = "JwtAuthenticationFilter")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
		setFilterProcessesUrl("/api/users/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			LoginRequestDTO requestDTO = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					requestDTO.getUsername(),
					requestDTO.getPassword(),
					null
				)
			);
		} catch (EOFException e) {
			log.error("로그인 요청이 비어 있거나 불완전합니다.");
			throw new LoginException("로그인 요청이 비어 있거나 불완전합니다.");
		} catch (IOException e) {
			log.error("회원을 찾을 수 없습니다.");
			throw new LoginException("회원을 찾을 수 없습니다.");
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
		String username = ((UserDetailsImpl)authResult.getPrincipal()).getUsername();
		UserRole role = ((UserDetailsImpl)authResult.getPrincipal()).getUser().getUserRole();

		String accessToken = jwtUtil.createToken(username, role);
		String refreshToken = jwtUtil.createRefreshToken(username, role);

		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
		response.addHeader(JwtUtil.REFRESHTOKEN_HEADER, refreshToken);

		jwtUtil.saveRefreshToken(refreshToken.substring(7));

		ResponseEntity<ResponseMessage<String>> responseEntity = createResponseEntity(HttpStatus.OK, "로그인에 성공했습니다.", authResult);
		log.info("User = {}, message ={}", username, "로그인에 성공했습니다.");

		String jsonResponse = new ObjectMapper().writeValueAsString(responseEntity.getBody());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonResponse);
		response.getWriter().flush();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
		log.error("{}: {}", failed.getClass().getSimpleName(), failed.getMessage());
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write("회원을 찾을 수 없습니다.");
		response.getWriter().flush();
	}

	private ResponseEntity<ResponseMessage<String>> createResponseEntity(HttpStatus status, String message, Authentication authResult) {
		ResponseMessage<String> responseMessage = ResponseMessage.<String>builder()
			.statusCode(status.value())
			.message(message)
			.data(authResult.getName())
			.build();

		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
}
