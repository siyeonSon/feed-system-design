package com.demo.feedsystemdesign.follow.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FollowRedisRepository {

    private final RedisTemplate<String, Long> redisTemplate;

    public void add(Long sourceId, Long targetId) {
        redisTemplate.opsForSet().add("followCache:targetId:" + targetId, sourceId);
    }
}
