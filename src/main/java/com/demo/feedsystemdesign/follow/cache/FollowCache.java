package com.demo.feedsystemdesign.follow.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class FollowCache {

    private static final String KEY = "followCache:targetId:";

    private final RedisTemplate<String, Long> redisTemplate;

    public void add(Long sourceId, Long targetId) {
        redisTemplate.opsForSet().add(KEY + targetId, sourceId);
    }

    public Set<Long> getFollowerIds(Long userId) {
        return redisTemplate.opsForSet().members(KEY + userId);
    }
}
