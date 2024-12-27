package com.spring.instafeed.profile.dto.request;

public record UpdateProfileRequestDto (
        Long profileId,
        String nickname,
        String imagePath,
        String content
) {}
