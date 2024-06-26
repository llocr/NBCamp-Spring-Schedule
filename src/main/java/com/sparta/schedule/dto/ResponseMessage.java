package com.sparta.schedule.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseMessage<T> {
	private Integer statusCode;
	private String message;
	private T data;
}
