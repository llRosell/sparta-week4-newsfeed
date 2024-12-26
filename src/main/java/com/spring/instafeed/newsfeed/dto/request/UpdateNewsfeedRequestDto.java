package com.spring.instafeed.newsfeed.dto.request;

public record UpdateNewsfeedRequestDto(
        Long newsfeedId,
        String content
) {
}