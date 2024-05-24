package com.sparta.schedule.exception;

public class CommentNotFoundException extends IllegalArgumentException {
	public CommentNotFoundException() {
		super("해당 댓글이 존재하지 않습니다.");
	}
}
