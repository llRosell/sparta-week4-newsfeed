package com.spring.instafeed.follower.service;

import com.spring.instafeed.base.Status;
import com.spring.instafeed.follower.dto.response.FollowerResponseDto;
import com.spring.instafeed.follower.entity.Follower;
import com.spring.instafeed.follower.repository.FollowerRepository;
import com.spring.instafeed.profile.entity.Profile;
import com.spring.instafeed.profile.repository.ProfileRepository;
import com.spring.instafeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {
    private final FollowerRepository followerRepository;
    private final ProfileRepository profileRepository;

    @Override
    public FollowerResponseDto sendFollowRequest(Long senderId, Long receiverId) {

        Profile sendingProfile = profileRepository.findByIdOrElseThrow(senderId);
        Profile receivingProfile = profileRepository.findByIdOrElseThrow(receiverId);

        Follower follower = new Follower(sendingProfile, receivingProfile, Status.PENDING);

        Follower savedFollower = followerRepository.save(follower);

        return FollowerResponseDto.toDto(savedFollower);
    }

    @Override
    public List<FollowerResponseDto> findAll() {

        List<FollowerResponseDto> allFollowers = new ArrayList<>();

        allFollowers = followerRepository.findAllExceptDeleted()
                .stream()
                .map(FollowerResponseDto::toDto)
                .toList();

        return allFollowers;
    }
}
