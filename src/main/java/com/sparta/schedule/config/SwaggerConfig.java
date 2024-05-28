package com.sparta.schedule.config;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sparta.schedule.jwt.JwtUtil;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI swaggerAPI() {
		return new OpenAPI()
			.components(new Components()
				.addSecuritySchemes(JwtUtil.AUTHORIZATION_HEADER, new SecurityScheme()
					.name(JwtUtil.AUTHORIZATION_HEADER)
					.type(SecurityScheme.Type.HTTP)
					.bearerFormat("JWT")))
			.addSecurityItem(new SecurityRequirement().addList(JwtUtil.AUTHORIZATION_HEADER))
			.info(new Info()
				.title("스프링 숙련주차 개인과제")
				.description("스프링 숙련주차 개인과제입니다.")
				.version("1.0.0"));
	}

	@Bean
	public OperationCustomizer globalHeader() {
		return (operation, handlerMethod) -> {
			operation.addParametersItem(new Parameter()
				.in(ParameterIn.HEADER.toString())
				.schema(new StringSchema().name(JwtUtil.REFRESHTOKEN_HEADER))
				.name(JwtUtil.REFRESHTOKEN_HEADER));

			return operation;
		};
	}
}
