package com.spring.instafeed.profile.dto.response;

import com.spring.instafeed.profile.entity.Profile;

import java.time.LocalDateTime;

public record CreateProfileResponseDto(
        Long id,
        Long userId,
        String nickname,
        String content,
        String imagePath,
        LocalDateTime createdAt
) {
    /**
     * Profile 객체를 CreateProfileResponseDto 로 변환하는 static 메서드
     *
     * @param profile 변환할 Profile 엔티티 객체
     * @return Profile 을 기반으로 생성된 CreateProfileResponseDto 객체
     */
    public static CreateProfileResponseDto toDto(Profile profile) {
        return new CreateProfileResponseDto(
                profile.getId(),
                profile.getUser().getId(),
                profile.getNickname(),
                profile.getContent(),
                profile.getImagePath(),
                profile.getCreatedAt()
        );
    }
}