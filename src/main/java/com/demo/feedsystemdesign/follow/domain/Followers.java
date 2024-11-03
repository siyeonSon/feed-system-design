package com.demo.feedsystemdesign.follow.domain;

import com.demo.feedsystemdesign.common.exception.FollowException;

import java.util.ArrayList;
import java.util.List;

import static com.demo.feedsystemdesign.common.exception.ErrorCode.SELF_FOLLOWING;

public class Followers {

    private final Long ownerId;
    private final List<Long> followers;

    public Followers(Long ownerId) {
        this(ownerId, new ArrayList<>());
    }

    public Followers(Long ownerId, List<Long> followers) {
        this.ownerId = ownerId;
        this.followers = followers;
    }

    public void add(Long followerId) {
        if (ownerId.equals(followerId)) {
            throw new FollowException(SELF_FOLLOWING);
        }
        followers.add(followerId);
    }

    public boolean contains(Long followerId) {
        return followers.contains(followerId);
    }

    public List<Long> findAll() {
        return followers;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}
