package com.demo.feedsystemdesign.feed.service;

import com.demo.feedsystemdesign.post.service.PostServiceV1;
import com.demo.feedsystemdesign.post.service.dto.PostResponse;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FeedServiceV2Test {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedServiceV2 feedService;

    @Autowired
    private PostServiceV1 postService;

    @Autowired
    private FeedPostInsertion feedPostInsertion;

    @Test
    void 사용자의_피드를_가져온다() {
        User user = userRepository.save(new User());
        PostResponse post = postService.createPost(user.getId(), "test content");
        feedPostInsertion.insert(user.getId(), post.postId());

        assertThat(feedService.getFeed(user.getId()).getPosts())
                .extracting("id")
                .containsExactly(post.postId());
    }

}
