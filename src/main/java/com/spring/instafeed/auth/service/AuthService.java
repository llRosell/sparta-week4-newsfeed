package com.spring.instafeed.auth.service;

import com.spring.instafeed.auth.domain.Email;
import com.spring.instafeed.auth.domain.Password;
import com.spring.instafeed.auth.domain.TokenProvider;
import com.spring.instafeed.auth.dto.request.LoginRequestDto;
import com.spring.instafeed.auth.dto.request.SignUpRequestDto;
import com.spring.instafeed.auth.dto.response.AccessTokenResponseDto;
import com.spring.instafeed.auth.repository.UserAuthRepositoryImpl;
import com.spring.instafeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final UserAuthRepositoryImpl userAuthRepository;

    /**
     * 회원 가입
     */
    public AccessTokenResponseDto signUpUser(SignUpRequestDto dto) {
        verifyEmail(dto);

        String encryptedPassword = Password.generateEncryptedPassword(dto.password())
                .getEncryptedPassword();

        User user = userAuthRepository.registerUser(new User(dto.name(), dto.email(), encryptedPassword));

        return createToken(user);
    }

    /**
     * 로그인
     */
    public AccessTokenResponseDto loginUser(LoginRequestDto dto) {
        User user = userAuthRepository.findByEmail(dto.email());

        verifyPassword(user.getPassword(), dto.password());

        return createToken(user);
    }

    /**
     * 사용된적이 있는 이메일인지 검증
     */
    private void verifyEmail(SignUpRequestDto dto) {
        Email email = Email.generateEmail(dto.email());

        if (userAuthRepository.existsEmail(email.getEmailText())) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    /**
     * 비밀번호 검증
     */
    private void verifyPassword(String storagePassword, String rawPassword) {
        if (!Password.generatePassword(storagePassword).matchPassword(rawPassword)) {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    /**
     * 유저 정보를 기반으로 토큰 생성
     */
    private AccessTokenResponseDto createToken(User user) {
        String token = tokenProvider.createToken(user);
        return new AccessTokenResponseDto(token, "Bearer", user.getId(), "create token");
    }
}