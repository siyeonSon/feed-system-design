package com.demo.feedsystemdesign.post.service;

import com.demo.feedsystemdesign.follow.service.FollowService;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class PostServiceV2AsyncTest {

    @SpyBean
    private PostServiceV2Async postService;

    @SpyBean
    private UserRepository userRepository;

    @SpyBean
    private FollowService followService;

    @MockBean
    private ApplicationEventPublisher eventPublisher;

    @Test
    void 사용자가_게시물을_작성하면_게시물_작성_이벤트가_발행된다() {
        User user = userRepository.save(new User());
        User follower = userRepository.save(new User());
        followService.follow(follower.getId(), user.getId());

        postService.createPost(user.getId(), "test content");

        verify(eventPublisher).publishEvent(any());
    }
}