package com.demo.feedsystemdesign.follow.domain;

import java.util.ArrayList;
import java.util.List;

public class Followers {

    private final List<Long> followers;

    public Followers() {
        this(new ArrayList<>());
    }

    public Followers(List<Long> followers) {
        this.followers = followers;
    }

    public void add(Long otherUserId) {
        followers.add(otherUserId);
    }

    public boolean contains(Long otherUserId) {
        return followers.contains(otherUserId);
    }
}
