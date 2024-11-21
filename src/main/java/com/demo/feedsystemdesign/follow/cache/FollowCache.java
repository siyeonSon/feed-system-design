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

    public void add(Long targetId, Long... sourceIds) {
        redisTemplate.opsForSet().add(KEY + targetId, sourceIds);
    }

    public Set<Long> getFollowerIds(Long userId) {
        return redisTemplate.opsForSet().members(KEY + userId);
    }

    public Boolean notHas(Long userId) {
        return Boolean.FALSE.equals(redisTemplate.opsForSet().getOperations().hasKey(KEY + userId));
    }

}
