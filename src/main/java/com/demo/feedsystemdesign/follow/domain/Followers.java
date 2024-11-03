package com.demo.feedsystemdesign.follow.domain;

import com.demo.feedsystemdesign.common.exception.FollowException;

import java.util.ArrayList;
import java.util.List;

import static com.demo.feedsystemdesign.common.exception.ErrorCode.SELF_FOLLOWING;

public class Followers {

    private final long ownerId;
    private final List<Follow> followers;

    public Followers(long ownerId) {
        this.ownerId = ownerId;
        this.followers = new ArrayList<>();
    }

    public void add(long followerId) {
        if (ownerId == followerId) {
            throw new FollowException(SELF_FOLLOWING);
        }
        followers.add(new Follow(ownerId, followerId));
    }

    public boolean contains(long followerId) {
        return followers.stream()
                .anyMatch(follow -> follow.getFollowerId() == followerId);
    }

    public List<Long> findAll() {
        return followers.stream()
                .map(Follow::getFollowerId)
                .toList();
    }

    public Long getOwnerId() {
        return ownerId;
    }
}
