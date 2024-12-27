package com.spring.instafeed.auth.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.instafeed.auth.domain.TokenProvider;
import com.spring.instafeed.auth.dto.response.ExceptionResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private static final String[] WHITE_LIST = {"/auth/signup", "/auth/login"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        // 토큰의 유효성 검사
        if (token == null || !tokenProvider.validateToken(token)) {
            handleTokenExceptionResponse(response);
            return;
        }

        Long userId = tokenProvider.getUserId(token);
        request.setAttribute("userId", userId);

        filterChain.doFilter(request, response);
    }

    /**
     * WHITE_LIST 의 경로는 Filter 를 무시함
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        for (String pattern : WHITE_LIST) {
            if (PatternMatchUtils.simpleMatch(pattern, requestURI)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Request Header 에서 토큰을 가져옴
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    private void handleTokenExceptionResponse(HttpServletResponse response) throws IOException {
        ExceptionResponseDto errorResponse = new ExceptionResponseDto(HttpServletResponse.SC_UNAUTHORIZED, HttpStatus.UNAUTHORIZED, "Invalid or missing token");
        ObjectMapper mapper = new ObjectMapper();

        response.setStatus(errorResponse.status().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
