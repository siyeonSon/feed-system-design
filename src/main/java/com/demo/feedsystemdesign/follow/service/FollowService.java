package com.demo.feedsystemdesign.follow.service;

import com.demo.feedsystemdesign.follow.domain.Followers;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FollowService {

    Map<Long, Followers> store = new HashMap<>();

    public void follow(Long userId, Long subjectId) {
        if (store.get(subjectId) == null) {
            store.put(subjectId, new Followers(subjectId));
        }
        Followers followers = store.get(subjectId);
        followers.add(userId);
    }

    public List<Long> getFollowers(Long userId) {
        return store.get(userId).findAll();
    }
}
