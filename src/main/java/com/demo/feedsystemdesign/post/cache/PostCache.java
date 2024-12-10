package com.demo.feedsystemdesign.post.cache;

import com.demo.feedsystemdesign.post.domain.Post;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class PostCache {

    private static final String KEY = "postCache:postId:";

    private final RedisTemplate<String, PostCacheDto> redisTemplate;

    public PostCache(RedisTemplate<String, PostCacheDto> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void add(Post post) {
        redisTemplate.opsForValue().set(KEY + post.getId(), PostCacheDto.of(post));
    }


    public PostCacheDto getById(Long postId) {
        return redisTemplate.opsForValue().get(KEY + postId);
    }
}
