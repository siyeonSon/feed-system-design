package com.demo.feedsystemdesign.post.service;

import com.demo.feedsystemdesign.follow.service.FollowService;
import com.demo.feedsystemdesign.post.event.PostCreatedEvent;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RecordApplicationEvents
class PostServiceV2AsyncTest {

    @Autowired
    private PostServiceV2Async postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowService followService;

    @Test
    void 사용자가_게시물을_작성하면_게시물_작성_이벤트가_발행된다(ApplicationEvents events) {
        User user = userRepository.save(new User());
        User follower = userRepository.save(new User());
        followService.follow(follower.getId(), user.getId());
        postService.createPost(user.getId(), "test content");

        assertThat(events.stream(PostCreatedEvent.class).count())
                        .isEqualTo(1);
    }
}