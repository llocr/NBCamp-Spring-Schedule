package com.sparta.schedule.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sparta.schedule.entity.UserRole;
import com.sparta.schedule.exception.TokenException;
import com.sparta.schedule.security.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// JWT 검증 및 인가
@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;

	public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String accessToken = jwtUtil.getAccessTokenFromHeader(request);
		String refreshToken = jwtUtil.getRefreshTokenFromHeader(request);

		if (StringUtils.hasText(accessToken)) {
			if (jwtUtil.validateToken(accessToken)) {
				Claims info = jwtUtil.getUserInfoFromToken(accessToken);
				try {
					setAuthentication(info.getSubject());
				} catch (Exception e) {
					throw new TokenException("인증 정보를 찾을 수 없습니다.");
				}

			} else if (!jwtUtil.validateToken(accessToken) && refreshToken != null) {
				//재발급 후 컨텍스트에 다시 넣기
				//리프레시 토큰 검증
				boolean validateRefreshToken = jwtUtil.validateToken(refreshToken);
				log.info("validateRefreshToken : " + validateRefreshToken);

				//리프레시 토큰 저장소 존재유무 확인
				boolean isRefreshToken = jwtUtil.existRefreshToken(refreshToken);
				log.info("refreshToken : " + isRefreshToken);

				if (validateRefreshToken && isRefreshToken) {
					Claims info = jwtUtil.getUserInfoFromToken(refreshToken);

					UserRole role = UserRole.valueOf(info.getAudience());
					log.info("role : " + role.name());

					String newAccessToken = jwtUtil.createToken(info.getSubject(), role);
					jwtUtil.setHeaderAccessToken(response, newAccessToken);

					try {
						setAuthentication(info.getSubject());
					} catch (Exception e) {
						throw new TokenException("인증 정보를 찾을 수 없습니다.");
					}
				} else {
					throw new TokenException("유효하지 않은 리프레시 토큰입니다. 다시 로그인 해주세요.");
				}
			}
		}

		filterChain.doFilter(request, response);
	}

	// 인증 처리
	public void setAuthentication(String username) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(username);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	// 인증 객체 생성
	private Authentication createAuthentication(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
