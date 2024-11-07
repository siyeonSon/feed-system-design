package com.demo.feedsystemdesign.feed.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedPost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_post_id")
    private Long id;

    private Long userId;
    private Long postId;

    public FeedPost(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }
}
