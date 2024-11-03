package com.demo.feedsystemdesign.feed.service;

import com.demo.feedsystemdesign.feed.domain.Feed;
import com.demo.feedsystemdesign.follow.service.FollowService;
import com.demo.feedsystemdesign.post.service.PostService;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FeedServiceTest {

    @Autowired
    private FeedService feedService;

    @Autowired
    private FollowService followService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 사용자의_피드를_가져온다() {
        User user = userRepository.save(new User());

        Feed feed = feedService.getFeed(user.getId());

        assertThat(feed.getPosts()).isEmpty();
    }

    @Test
    void 피드에_팔로우한_사용자의_게시물이_포함된다() {
        User user = userRepository.save(new User());
        User subject = userRepository.save(new User());
        followService.follow(user.getId(), subject.getId());
        postService.createPost(subject.getId(), "test content");
        postService.createPost(subject.getId(), "test content");

        Feed feed = feedService.getFeed(user.getId());

        assertThat(feed.getPosts())
                .allMatch(post -> post.getUserId().equals(subject.getId()));
    }
}
