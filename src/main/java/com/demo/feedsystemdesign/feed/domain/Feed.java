package com.demo.feedsystemdesign.feed.domain;

import com.demo.feedsystemdesign.post.domain.Post;
import com.demo.feedsystemdesign.post.service.dto.PostResponse;

import java.util.List;

public class Feed {
    private final List<PostResponse> posts;

    public Feed(List<PostResponse> posts) {
        this.posts = posts;
    }

    public static Feed of(List<Post> posts) {
        return new Feed(
                posts.stream()
                        .map(PostResponse::of)
                        .toList()
        );
    }

    public List<PostResponse> getPosts() {
        return posts;
    }
}
