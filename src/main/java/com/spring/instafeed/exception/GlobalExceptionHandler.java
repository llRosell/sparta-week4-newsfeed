package com.spring.instafeed.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 기타 예외
     * @param ex : Exception
     * @return : Map<String, Object>
     *          - "status": HTTP 상태 코드 (HttpStatus)
     *          - "message": 에러 메세지 (String)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        responseBody.put("message", "An unexpected error occurred: " + ex.getMessage());

        return ResponseEntity
                .status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                .body(responseBody);
    }
}
