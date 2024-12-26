package com.spring.instafeed.auth.dto.response;

import org.springframework.http.HttpStatus;

public record ExceptionResponseDto(int code, HttpStatus status, String message) {
}
