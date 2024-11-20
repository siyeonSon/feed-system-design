package com.demo.feedsystemdesign.follow.cache;

import com.demo.feedsystemdesign.support.general.RedisTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RedisTest
class FollowRedisRepositoryTest {

    @Autowired
    private FollowRedisRepository followRedisRepository;

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    @Test
    void 팔로우_관계를_캐싱한다() {
        Long sourceId = 1L;
        Long targetId = 2L;

        followRedisRepository.add(sourceId, targetId);

        assertThat(redisTemplate.opsForSet().isMember("followCache:targetId:" + targetId, sourceId)).isTrue();
    }
}
