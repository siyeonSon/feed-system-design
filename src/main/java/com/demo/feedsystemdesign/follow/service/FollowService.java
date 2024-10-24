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
    Map<Long, Followers> store = new HashMap<>();

    public FollowService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO: use primitive type
    public void follow(Long userId, Long subjectId) {
        validateExists(userId);
        validateExists(subjectId);

        if (store.get(subjectId) == null) {
            store.put(subjectId, new Followers(subjectId));
        }
        Followers followers = store.get(subjectId);
        followers.add(userId);
    }

    public List<Long> getFollowers(Long userId) {
        return store.get(userId).findAll();
    }

    // TODO: 중복 코드 제거
    private void validateExists(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }
}
