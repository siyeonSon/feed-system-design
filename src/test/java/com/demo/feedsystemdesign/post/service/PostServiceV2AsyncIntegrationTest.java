package com.demo.feedsystemdesign.post.service;

import com.demo.feedsystemdesign.feed.service.FeedServiceV2Async;
import com.demo.feedsystemdesign.follow.service.FollowService;
import com.demo.feedsystemdesign.post.service.dto.PostResponse;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PostServiceV2AsyncIntegrationTest {

    @Autowired
    private PostServiceV2Async postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowService followService;

    @Autowired
    private FeedServiceV2Async feedService;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Test
    void 사용자가_게시물을_작성하면_팔로워들의_피드에_해당_게시물을_삽입한다() {
        User user = userRepository.save(new User());
        User follower = userRepository.save(new User());
        followService.follow(follower.getId(), user.getId());

        PostResponse post = postService.createPost(user.getId(), "test content");
        try {
            taskExecutor.getThreadPoolExecutor().awaitTermination(1, TimeUnit.SECONDS);
        } catch (Exception ignored) { }

        assertThat(feedService.getFeed(follower.getId()).getPosts())
                .extracting("id")
                .containsExactly(post.postId());
    }
}
