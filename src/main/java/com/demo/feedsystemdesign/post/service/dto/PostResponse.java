package com.demo.feedsystemdesign.post.service.dto;

import com.demo.feedsystemdesign.post.domain.Post;

public record PostResponse(
        Long postId,
        String content,
        Long userId) {
    public static PostResponse of(Post post) {
        return new PostResponse(post.getId(), post.getContent(), post.getUserId());
    }
}
