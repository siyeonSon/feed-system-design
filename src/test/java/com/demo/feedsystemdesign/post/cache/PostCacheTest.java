package com.demo.feedsystemdesign.post.cache;

import com.demo.feedsystemdesign.post.domain.Post;
import com.demo.feedsystemdesign.support.general.RedisTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@RedisTest
class PostCacheTest {

    @Autowired
    private PostCache postCache;

    @Autowired
    private RedisTemplate<String, PostCacheDto> redisTemplate;

    @Test
    void 게시물을_캐싱한다() {
        Post post = new Post(1L, "test content");
        ReflectionTestUtils.setField(post, "id", 1L);

        postCache.add(post);

        var postCacheDto = redisTemplate.opsForValue().get("postCache:postId:" + post.getId());
        assertThat(postCacheDto).isNotNull();
    }

    @Test
    void 게시물을_가져온다() {
        Post post = new Post(1L, "test content");
        ReflectionTestUtils.setField(post, "id", 1L);
        postCache.add(post);

        PostCacheDto postCacheDto = postCache.getById(post.getId());

        assertThat(postCacheDto.id()).isEqualTo(post.getId());
    }

}
