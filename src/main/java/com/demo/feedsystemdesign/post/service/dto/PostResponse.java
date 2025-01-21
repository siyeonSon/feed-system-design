package com.demo.feedsystemdesign.post.service.dto;

import com.demo.feedsystemdesign.post.cache.PostCacheDto;
import com.demo.feedsystemdesign.post.domain.Post;

public record PostResponse(
        Long userId,
        Long postId,
        String content
) {
    public static PostResponse of(Post post) {
        return new PostResponse(
                post.getUserId(),
                post.getId(),
                post.getContent()
        );
    }

    public static PostResponse of(PostCacheDto post) {
        return new PostResponse(
                post.userId(),
                post.id(),
                post.content()
        );
    }
}
