package com.sparta.schedule.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sparta.schedule.entity.UserRefreshToken;
import com.sparta.schedule.entity.UserRole;
import com.sparta.schedule.repository.UserRefreshTokenRepository;
import com.sparta.schedule.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtUtil {
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String REFRESHTOKEN_HEADER = "RefreshToken";
	public static final String AUTHORIZATION_KEY = "aud";
	public static final String BEARER_PREFIX = "Bearer ";
	private final long TOKEN_TIME = 60 * 60 * 1000L;
	private final long REFRESH_TIME = 24 * 60 * 60 * 1000L;

	private final UserRefreshTokenRepository userRefreshTokenRepository;

	public JwtUtil(UserRefreshTokenRepository userRefreshTokenRepository, UserRepository userRepository) {
		this.userRefreshTokenRepository = userRefreshTokenRepository;
	}

	@Value("${jwt.secret.key}")
	private String secretKey;

	private Key key;
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
	}

	// JWT 토큰 생성하기
	public String createToken(String username, UserRole role) {
		Date date = new Date();

		return BEARER_PREFIX +
			Jwts.builder()
				.setSubject(username)
				.claim(AUTHORIZATION_KEY, role)
				.setExpiration(new Date(date.getTime() + TOKEN_TIME))
				.setIssuedAt(date)
				.signWith(key, signatureAlgorithm)
				.compact();
	}

	// 리프레시 토큰 생성하기
	public String createRefreshToken(String username, UserRole role) {
		Date date = new Date();

		return BEARER_PREFIX +
			Jwts.builder()
				.setSubject(username)
				.claim(AUTHORIZATION_KEY, role)
				.setExpiration(new Date(date.getTime() + REFRESH_TIME))
				.setIssuedAt(date)
				.signWith(key, signatureAlgorithm)
				.compact();
	}

	// 헤더에서 access token 가져오기
	public String getAccessTokenFromHeader(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}

	//헤더에서 refresh token 가져오기
	public String getRefreshTokenFromHeader(HttpServletRequest request) {
		String refreshToken = request.getHeader(REFRESHTOKEN_HEADER);
		if (StringUtils.hasText(refreshToken) && refreshToken.startsWith(BEARER_PREFIX)) {
			return refreshToken.substring(7);
		}
		return null;
	}

	// JWT 토큰 검증하기
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException | SignatureException |
				 ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			return false;
		}
	}

	// JWT 토큰에서 사용자 정보 가져오기
	public Claims getUserInfoFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	//RefreshToken 존재 유무 확인
	public boolean existRefreshToken(String refreshToken) {
		Optional<UserRefreshToken> token = userRefreshTokenRepository.findByRefreshToken(refreshToken);

		return token.isPresent();
	}

	//access token 헤더 설정
	public void setHeaderAccessToken(HttpServletResponse response, String newAccessToken) {
		response.setHeader(AUTHORIZATION_HEADER, newAccessToken);
	}

	//리프레시 토큰 저장하기
	public void saveRefreshToken(String refreshToken) {
		UserRefreshToken token = new UserRefreshToken(refreshToken);
		userRefreshTokenRepository.save(token);
	}
}
