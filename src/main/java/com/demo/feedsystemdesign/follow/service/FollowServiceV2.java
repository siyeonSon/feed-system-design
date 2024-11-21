package com.demo.feedsystemdesign.follow.service;

import com.demo.feedsystemdesign.common.exception.NotFoundException;
import com.demo.feedsystemdesign.follow.cache.FollowCache;
import com.demo.feedsystemdesign.follow.domain.Follow;
import com.demo.feedsystemdesign.follow.domain.FollowRepository;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.demo.feedsystemdesign.common.exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class FollowServiceV2 {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FollowCache followCache;

    @Transactional
    public void follow(Long sourceId, Long targetId) {
        validateExists(sourceId);
        validateExists(targetId);

        followRepository.save(new Follow(sourceId, targetId));
        followCache.add(sourceId, targetId);  // TODO: 비동기 이벤트 발생
    }

    public List<Long> getFollowers(Long userId) {
        Set<Long> cachedFollowerIds = followCache.getFollowerIds(userId);
        if (cachedFollowerIds.isEmpty() && followCache.notHas(userId)) {
            Set<Long> followerIds = followRepository.findAllByTargetId(userId)
                    .stream()
                    .map(Follow::getSourceId)
                    .collect(Collectors.toSet());
            followerIds.forEach(followerId -> followCache.add(followerId, userId));
        }
        return cachedFollowerIds.stream().toList();
    }

    public List<Long> getFollowings(Long userId) {
        return followRepository.findAllBySourceId(userId)
                .stream()
                .map(Follow::getTargetId)
                .toList();
    }

    private void validateExists(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }
}
