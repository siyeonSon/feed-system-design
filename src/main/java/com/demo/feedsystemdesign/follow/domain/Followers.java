package com.demo.feedsystemdesign.follow.domain;

import com.demo.feedsystemdesign.common.exception.FollowException;

import java.util.ArrayList;
import java.util.List;

import static com.demo.feedsystemdesign.common.exception.ErrorCode.SELF_FOLLOWING;

public class Followers {

    private final long ownerId;
    private final List<Long> followers;

    public Followers(long ownerId) {
        this(ownerId, new ArrayList<>());
    }

    public Followers(long ownerId, List<Long> followers) {
        this.ownerId = ownerId;
        this.followers = followers;
    }

    public void add(long followerId) {
        if (ownerId == followerId) {
            throw new FollowException(SELF_FOLLOWING);
        }
        followers.add(followerId);
    }

    public boolean contains(long followerId) {
        return followers.contains(followerId);
    }

    public List<Long> findAll() {
        return followers;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}
