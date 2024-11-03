package com.demo.feedsystemdesign.follow.service;

import com.demo.feedsystemdesign.common.exception.NotFoundException;
import com.demo.feedsystemdesign.follow.domain.Followers;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.demo.feedsystemdesign.common.exception.ErrorCode.USER_NOT_FOUND;

@Service
public class FollowService {

    private final UserRepository userRepository;
    Map<Long, Followers> followersRepository = new HashMap<>();

    public FollowService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO: use primitive type
    public void follow(Long userId, Long subjectId) {
        validateExists(userId);
        validateExists(subjectId);

        if (followersRepository.get(subjectId) == null) {
            followersRepository.put(subjectId, new Followers(subjectId));
        }
        Followers followers = followersRepository.get(subjectId);
        followers.add(userId);
    }

    public List<Long> getFollowers(Long userId) {
        return followersRepository.get(userId).findAll();
    }

    public List<Long> getFollowings(Long userId) {
        return followersRepository.values().stream()
                .filter(followers -> followers.contains(userId))
                .map(Followers::getUserId)
                .toList();
    }

    private void validateExists(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }
}
