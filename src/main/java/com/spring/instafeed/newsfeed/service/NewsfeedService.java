package com.spring.instafeed.newsfeed.service;

import com.spring.instafeed.newsfeed.dto.response.ContentsWrapperResponseDto;
import com.spring.instafeed.newsfeed.dto.response.CreateNewsfeedResponseDto;
import com.spring.instafeed.newsfeed.dto.response.ReadNewsfeedResponseDto;
import com.spring.instafeed.newsfeed.dto.response.UpdateNewsfeedResponseDto;
import org.springframework.data.domain.Pageable;

public interface NewsfeedService {

    CreateNewsfeedResponseDto createNewsfeed(
            Long profileId,
            String content,
            String imagePath
    );

    ContentsWrapperResponseDto readAllNewsfeeds(Pageable pageable);

    ReadNewsfeedResponseDto readNewsfeedById(Long newsfeedId);

    UpdateNewsfeedResponseDto updateNewsfeed(
            Long id,
            String content
    );

    void deleteNewsfeed(Long id);
}