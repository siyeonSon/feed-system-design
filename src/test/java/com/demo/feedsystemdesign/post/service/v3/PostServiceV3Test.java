package com.demo.feedsystemdesign.post.service.v3;

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
class PostServiceV3Test {

    @Autowired
    private PostServiceV3 postService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 사용자가_게시물을_작성하면_게시물_작성_이벤트가_발행된다(ApplicationEvents events) {
        User user = userRepository.save(new User());
        postService.createPost(user.getId(), "test content");

        assertThat(events.stream(PostCreatedEvent.class).count())
                .isEqualTo(1);
    }
}