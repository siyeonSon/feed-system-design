package com.demo.feedsystemdesign.follow.cache;

import com.demo.feedsystemdesign.support.general.RedisTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RedisTest
class FollowCacheTest {

    @Autowired
    private FollowCache followCache;

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    @Test
    void 팔로우_관계를_캐싱한다() {
        Long sourceId = 1L;
        Long targetId = 2L;

        followCache.add(targetId, sourceId);

        assertThat(redisTemplate.opsForSet().isMember("followCache:targetId:" + targetId, sourceId)).isTrue();
    }

    @Test
    void 팔로워들을_가져온다() {
        Long userId = 1L;
        Long followerId = 2L;
        Long otherFollowerId = 3L;
        Long anotherFollowerId = 4L;
        followCache.add(userId, followerId, otherFollowerId, anotherFollowerId);

        Set<Long> followerIds = followCache.getFollowerIds(userId);

        assertThat(followerIds).containsExactly(followerId, otherFollowerId, anotherFollowerId);
    }

}
