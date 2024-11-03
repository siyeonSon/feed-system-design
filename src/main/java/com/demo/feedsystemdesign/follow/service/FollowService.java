package com.demo.feedsystemdesign.follow.service;

import com.demo.feedsystemdesign.common.exception.NotFoundException;
import com.demo.feedsystemdesign.follow.domain.Follow;
import com.demo.feedsystemdesign.follow.domain.FollowRepository;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.demo.feedsystemdesign.common.exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public void follow(long sourceId, long targetId) {
        validateExists(sourceId);
        validateExists(targetId);

        followRepository.save(new Follow(sourceId, targetId));
    }

    public List<Long> getFollowers(long userId) {
        return followRepository.findAllByTargetId(userId)
                .stream()
                .map(Follow::getSourceId)
                .toList();
    }

    public List<Long> getFollowings(long userId) {
        return followRepository.findAllBySourceId(userId)
                .stream()
                .map(Follow::getTargetId)
                .toList();
    }

    private void validateExists(long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }
}
