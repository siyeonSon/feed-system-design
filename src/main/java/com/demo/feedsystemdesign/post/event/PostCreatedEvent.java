package com.demo.feedsystemdesign.post.event;

public class PostCreatedEvent {
    private final Long userId;
    private final Long postId;

    public PostCreatedEvent(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPostId() {
        return postId;
    }

}
