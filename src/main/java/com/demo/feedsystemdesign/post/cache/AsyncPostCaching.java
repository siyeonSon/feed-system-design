package com.demo.feedsystemdesign.post.cache;

import com.demo.feedsystemdesign.post.domain.Post;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class AsyncPostCaching {

    private final PostCache postCache;

    public AsyncPostCaching(PostCache postCache) {
        this.postCache = postCache;
    }

    @Async
    public CompletableFuture<Void> cache(Post post) {
        postCache.add(post);
        return CompletableFuture.completedFuture(null);
    }

}
