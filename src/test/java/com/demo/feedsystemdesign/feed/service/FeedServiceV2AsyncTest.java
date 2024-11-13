package com.demo.feedsystemdesign.feed.service;

import com.demo.feedsystemdesign.follow.service.FollowService;
import com.demo.feedsystemdesign.post.domain.Post;
import com.demo.feedsystemdesign.post.domain.PostRepository;
import com.demo.feedsystemdesign.post.event.PostCreatedEvent;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FeedServiceV2AsyncTest {

    @Autowired
    private FeedServiceV2Async feedService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowService followService;

    @Autowired
    private PostRepository postRepository;

    @Test
    void 게시물이_작성되면_작성자_팔로워들의_피드에_해당_게시물을_삽입한다() {
        User user = userRepository.save(new User());
        User follower = userRepository.save(new User());
        followService.follow(follower.getId(), user.getId());
        Post post = postRepository.save(new Post(user.getId(), "test content"));

        feedService.onPostCreated(new PostCreatedEvent(user.getId(), post.getId()));

        assertThat(feedService.getFeed(follower.getId()).getPosts())
                .extracting("id")
                .containsExactly(post.getId());
    }

}