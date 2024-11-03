package com.demo.feedsystemdesign.follow.domain;

import com.demo.feedsystemdesign.common.BaseTimeEntity;

public class Follow extends BaseTimeEntity {

    private long ownerId;
    private long followerId;

    public Follow(long ownerId, long followerId) {
        this.ownerId = ownerId;
        this.followerId = followerId;
    }

    public long getFollowerId() {
        return followerId;
    }
}
