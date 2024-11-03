package com.demo.feedsystemdesign.feed.domain;

import com.demo.feedsystemdesign.post.domain.Post;

import java.util.List;

public class Feed {
    private final List<Post> posts;

    public Feed(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }
}
