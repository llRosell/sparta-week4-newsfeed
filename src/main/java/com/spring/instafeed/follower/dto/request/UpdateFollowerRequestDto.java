package com.spring.instafeed.follower.dto.request;

import com.spring.instafeed.common.Status;

public record UpdateFollowerRequestDto(
        Long followerId,
        Status status
) {
}