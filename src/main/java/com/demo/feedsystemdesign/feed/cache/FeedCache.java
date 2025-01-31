package com.demo.feedsystemdesign.feed.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedCache {

    public static final String KEY = "feedCache:userId:";

    private final RedisTemplate<String, Long> redisTemplate;

    public FeedCache(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void add(Long userId, Long postId) {
        redisTemplate.opsForList().leftPush(KEY + userId, postId);
    }

    public List<Long> getBy(Long userId) {
        return redisTemplate.opsForList().range(KEY + userId, 0, -1);
    }

    public Boolean notHas(Long userId) {
        return Boolean.FALSE.equals(redisTemplate.opsForList().getOperations().hasKey(KEY + userId));
    }
    
}
