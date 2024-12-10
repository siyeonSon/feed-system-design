package com.demo.feedsystemdesign.post.cache;

import com.demo.feedsystemdesign.post.domain.Post;

import java.time.LocalDateTime;

public record PostCacheDto(
        Long id,
        Long userId,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static PostCacheDto of(Post post) {
        return new PostCacheDto(
                post.getId()
                ,post.getUserId()
                ,post.getContent()
                ,post.getCreatedAt()
                ,post.getModifiedAt()
        );
    }
}
