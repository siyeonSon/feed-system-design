package com.demo.feedsystemdesign.follow.domain;

import com.demo.feedsystemdesign.common.exception.ErrorCode;
import com.demo.feedsystemdesign.common.exception.FollowException;

import java.util.ArrayList;
import java.util.List;

import static com.demo.feedsystemdesign.common.exception.ErrorCode.SELF_FOLLOWING;

public class Followers {

    private final Long userId;
    private final List<Long> followers;

    public Followers(Long userId) {
        this(userId, new ArrayList<>());
    }

    public Followers(Long userId, List<Long> followers) {
        this.userId = userId;
        this.followers = followers;
    }

    public void add(Long otherUserId) {
        if (userId.equals(otherUserId)) {
            throw new FollowException(SELF_FOLLOWING);
        }
        followers.add(otherUserId);
    }

    public boolean contains(Long otherUserId) {
        return followers.contains(otherUserId);
    }

    public List<Long> findAll() {
        return followers;
    }

    public Long getUserId() {
        return userId;
    }
}
