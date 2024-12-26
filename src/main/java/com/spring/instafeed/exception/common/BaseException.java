package com.spring.instafeed.exception.common;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public interface BaseException {

    int getCode();

    HttpStatus getStatus();

    String getMessage();

    default Map<String, Object> createExceptionResponseBody(BaseException ex) {
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", ex.getCode());
        responseBody.put("status", ex.getStatus());
        responseBody.put("message", ex.getMessage());

        return responseBody;
    }
}
