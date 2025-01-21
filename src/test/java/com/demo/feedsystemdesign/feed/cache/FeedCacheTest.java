package com.demo.feedsystemdesign.feed.cache;

import com.demo.feedsystemdesign.support.general.RedisTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RedisTest
class FeedCacheTest {

    @Autowired
    private FeedCache feedCache;

    @Test
    void 피드를_캐싱한다() {
        Long userId = 1L;
        feedCache.add(userId, 1L);
        feedCache.add(userId, 2L);
        feedCache.add(userId, 3L);

        List<Long> postIds = feedCache.getBy(userId);

        assertThat(postIds).containsExactly(3L, 2L, 1L);
    }

}