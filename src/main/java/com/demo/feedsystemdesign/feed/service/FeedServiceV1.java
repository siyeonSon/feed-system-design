package com.demo.feedsystemdesign.feed.service;

import com.demo.feedsystemdesign.feed.domain.Feed;
import com.demo.feedsystemdesign.follow.service.FollowService;
import com.demo.feedsystemdesign.post.domain.Post;
import com.demo.feedsystemdesign.post.service.PostServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedServiceV1 {

    private final FollowService followService;
    private final PostServiceV1 postService;

    public Feed getFeed(Long userId) {
        List<Long> followers = followService.getFollowings(userId);
        List<Post> posts = followers.stream()
                .flatMap(followerId -> postService.getPosts(followerId).stream())
                .toList();
        return new Feed(posts);
    }

}
