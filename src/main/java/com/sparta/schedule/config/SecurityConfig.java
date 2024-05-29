package com.sparta.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sparta.schedule.jwt.JwtAuthorizationFilter;
import com.sparta.schedule.jwt.JwtExceptionFilter;
import com.sparta.schedule.jwt.JwtUtil;
import com.sparta.schedule.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final JwtExceptionFilter jwtExceptionFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public JwtAuthorizationFilter jwtAuthorizationFilter() {
		return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtExceptionFilter, UsernamePasswordAuthenticationFilter.class);

		http.csrf((csrf) -> csrf.disable());

		http.sessionManagement((sessionManagement) ->
			sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests
				.requestMatchers("/swagger-ui/**", "/api-docs/**", "/swagger-resources/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/schedules/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/users/signup").permitAll()
				.anyRequest().authenticated()
		);

		http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
