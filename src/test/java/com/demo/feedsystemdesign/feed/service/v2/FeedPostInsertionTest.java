package com.demo.feedsystemdesign.feed.service.v2;

import com.demo.feedsystemdesign.post.service.dto.PostResponse;
import com.demo.feedsystemdesign.post.service.v2.PostServiceV2;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FeedPostInsertionTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostServiceV2 postService;

    @Autowired
    private FeedServiceV2 feedService;

    @Autowired
    private FeedPostInsertion feedPostInsertion;

    @Test
    void 사용자의_피드에_게시물을_삽입한다() {
        User user = userRepository.save(new User());
        PostResponse post = postService.createPost(user.getId(), "test content");

        feedPostInsertion.insert(user.getId(), post.postId());

        assertThat(feedService.getFeed(user.getId()).getPosts())
                .extracting("id")
                .containsExactly(post.postId());
    }
}
