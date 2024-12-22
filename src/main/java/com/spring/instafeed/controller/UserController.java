package com.spring.instafeed.controller;


import com.spring.instafeed.dto.user.request.SignUpUserRequestDto;
import com.spring.instafeed.dto.user.response.UserResponseDto;
import com.spring.instafeed.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    /**
     * 기능
     * 회원가입
     *
     * @param requestDto : SignUpUserRequestDto
     * @return UserResponseDto, HttpStatus.CREATED
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody SignUpUserRequestDto requestDto) {
        UserResponseDto responseDto = userService.signUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * 기능
     * 회원 정보 조회
     *
     * @param id : 조회하려는 사용자의 id
     * @return UserResponseDto, HttpStatus.OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.findById(id);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}