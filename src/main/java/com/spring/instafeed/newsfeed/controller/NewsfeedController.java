package com.spring.instafeed.newsfeed.controller;

import com.spring.instafeed.newsfeed.dto.request.CreateNewsfeedRequestDto;
import com.spring.instafeed.newsfeed.dto.request.DeleteNewsfeedRequestDto;
import com.spring.instafeed.newsfeed.dto.request.UpdateNewsfeedRequestDto;
import com.spring.instafeed.newsfeed.dto.response.ContentsWrapperResponseDto;
import com.spring.instafeed.newsfeed.dto.response.CreateNewsfeedResponseDto;
import com.spring.instafeed.newsfeed.dto.response.ReadNewsfeedResponseDto;
import com.spring.instafeed.newsfeed.dto.response.UpdateNewsfeedResponseDto;
import com.spring.instafeed.newsfeed.service.NewsfeedServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsfeeds")
@RequiredArgsConstructor
public class NewsfeedController {

    // 속성
    private final NewsfeedServiceImpl newsfeedService;

    /**
     * 기능
     * 게시물 생성
     *
     * @param requestDto : 게시물 생성 요청에 해당하는 Request DTO
     * @return NewsfeedResponseDto
     */
    @PostMapping
    public ResponseEntity<CreateNewsfeedResponseDto> createNewsfeed(
            @RequestBody CreateNewsfeedRequestDto requestDto
    ) {
        CreateNewsfeedResponseDto responseDto = newsfeedService
                .createNewsfeed(
                        requestDto.profileId(),
                        requestDto.content(),
                        requestDto.imagePath()
                );
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * 기능
     * 게시물 목록을 페이징 조회
     *
     * @param page : 조회할 페이지 번호 (기본값: 0)
     * @param size : 조회할 페이지 크기 (기본값: 10)
     * @return ResponseEntity<Page < NewsfeedResponseDto>> 페이징 처리된 게시물 목록
     */
    @GetMapping("/list")
    public ResponseEntity<ContentsWrapperResponseDto> readAllNewsfeeds(
            @RequestParam(value = "page", defaultValue = "1") int page, // 기본값: 1페이지
            @RequestParam(value = "size", defaultValue = "10") int size // 기본값: 10개 항목
    ) {
        page = Math.max(page - 1, 0); // 1부터 시작하는 페이지 번호를 0부터 시작하도록 조정
        Pageable pageable = PageRequest.of(page, size);

        ContentsWrapperResponseDto responseDto = newsfeedService
                .readAllNewsfeeds(pageable);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 뉴스피드 단건 조회
     * JWT에서 사용자 정보를 추출하고, 주어진 뉴스피드 ID를 기반으로 단건 조회를 수행.
     *
     * @param newsfeedId : 조회할 뉴스피드 ID
     * @return 조회된 뉴스피드 정보를 담은 Response DTO
     */
    @GetMapping("/{newsfeedId}")
    public ResponseEntity<ReadNewsfeedResponseDto> readNewsfeedById(
            @PathVariable Long newsfeedId
    ) {
        ReadNewsfeedResponseDto responseDto = newsfeedService
                .readNewsfeedById(newsfeedId);

        return ResponseEntity.ok(responseDto);
    }

    /**
     * 기능
     * 게시물 내용 수정
     *
     * @param requestDto : 게시물 수정 요청 정보를 담고 있는 DTO
     * @return NewsfeedCommonResponseDto 게시물 정보 반환 DTO
     */
    @PatchMapping
    public ResponseEntity<UpdateNewsfeedResponseDto> updateNewsfeed(
            @RequestBody UpdateNewsfeedRequestDto requestDto
    ) {
        UpdateNewsfeedResponseDto responseDto = newsfeedService
                .updateNewsfeed(
                        requestDto.newsfeedId(),
                        requestDto.content()
                );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 기능
     * 게시물 단건 삭제
     *
     * @return 상태 코드 메시지(200 OK)만 반환
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteNewsfeed(
            @RequestBody DeleteNewsfeedRequestDto requestDto
    ) {

        newsfeedService.deleteNewsfeed(requestDto.newsfeedId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}