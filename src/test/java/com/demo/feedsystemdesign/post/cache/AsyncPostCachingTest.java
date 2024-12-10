package com.demo.feedsystemdesign.post.cache;

import com.demo.feedsystemdesign.post.domain.Post;
import com.demo.feedsystemdesign.support.general.RedisTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@RedisTest
class AsyncPostCachingTest {

    @Autowired
    private AsyncPostCaching asyncPostCaching;

    @Autowired
    private PostCache postCache;

    @Test
    void 게시물이_비동기적으로_캐싱된다() throws ExecutionException, InterruptedException {
        Post post = new Post(1L, "test content");

        CompletableFuture<Void> future = asyncPostCaching.cache(post);
        future.get();

        assertThat(postCache.getById(post.getId())).isNotNull();
    }
}