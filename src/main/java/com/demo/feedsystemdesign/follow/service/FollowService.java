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
    private final Map<Long, Followers> followersRepository = new HashMap<>();

    public FollowService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void follow(long userId, long subjectId) {
        validateExists(userId);
        validateExists(subjectId);

        if (followersRepository.get(subjectId) == null) {
            followersRepository.put(subjectId, new Followers(subjectId));
        }
        Followers followers = followersRepository.get(subjectId);
        followers.add(userId);
    }

    public List<Long> getFollowers(long userId) {
        return followersRepository.get(userId).findAll();
    }

    public List<Long> getFollowings(long userId) {
        return followersRepository.values().stream()
                .filter(followers -> followers.contains(userId))
                .map(Followers::getOwnerId)
                .toList();
    }

    private void validateExists(long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }
}
