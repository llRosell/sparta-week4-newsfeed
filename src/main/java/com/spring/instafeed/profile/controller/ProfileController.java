package com.spring.instafeed.profile.controller;

import com.spring.instafeed.profile.dto.request.*;
import com.spring.instafeed.profile.dto.response.*;
import com.spring.instafeed.profile.service.ProfileServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    // 속성
    private final ProfileServiceImpl profileService;

    /**
     * 기능
     * 프로필 생성
     *
     * @param requestDto : 프로필 생성 요청 DTO (userId 포함)
     * @return CreateProfileResponseDto
     */
    @PostMapping
    public ResponseEntity<CreateProfileResponseDto> createProfile(
            @RequestBody CreateProfileRequestDto requestDto
    ) {
        CreateProfileResponseDto responseDto = profileService
                .createProfile(
                        requestDto.userId(),
                        requestDto.nickname(),
                        requestDto.content(),
                        requestDto.imagePath()
                );
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * 기능
     * 프로필 목록 조회
     *
     * @return List<QueryProfileResponseDto> 모든 프로필 정보를 담은 리스트
     */
    @GetMapping("/list")
    public ResponseEntity<List<ReadProfileResponseDto>> readAllProfiles() {
        List<ReadProfileResponseDto> allProfiles = new ArrayList<>();

        allProfiles = profileService.readAllProfiles();

        // todo 깡통 리스트 나오지 않도록 리팩토링 필요
        return new ResponseEntity<>(allProfiles, HttpStatus.OK);
    }

    /**
     * 프로필 단건 조회
     * 주어진 프로필 ID를 바탕으로 특정 프로필을 조회하는 엔드포인트.
     *
     * @param request   : JWT 인증 정보를 포함한 HttpServletRequest
     * @param profileId : 조회할 프로필의 ID
     * @return 조회된 프로필 정보를 담은 Response DTO
     */
    @GetMapping("/{profileId}")
    public ResponseEntity<ReadProfileResponseDto> readProfileById(
            HttpServletRequest request,
            @PathVariable("profileId") Long profileId
    ) {
        // JWT에서 사용자 ID 추출
        Long userId = (Long) request.getAttribute("userId");

        // 서비스에서 프로필 정보 조회
        ReadProfileResponseDto profile = profileService
                .readProfileById(userId, profileId);

        return ResponseEntity.ok(profile);
    }

    /**
     * 기능
     * 프로필 전체 수정
     *
     * @param requestDto : 수정할 프로필 정보를 담은 요청 DTO
     * @return 수정된 프로필 정보를 담은 응답 DTO
     */
    @PutMapping
    public ResponseEntity<UpdateProfileResponseDto> updateProfile(
            @RequestBody UpdateProfileRequestDto requestDto
    ) {

        UpdateProfileResponseDto responseDto = profileService
                .updateProfile(
                        requestDto.profileId(),
                        requestDto.nickname(),
                        requestDto.content(),
                        requestDto.imagePath()
                );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 프로필 삭제
     * 특정 프로필을 논리적으로 삭제하는 엔드포인트입니다.
     *
     * @return 삭제된 프로필 정보
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(
            @RequestBody DeleteProfileRequestDto requestDto
    ) {
        profileService.deleteProfile(requestDto.profileId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}