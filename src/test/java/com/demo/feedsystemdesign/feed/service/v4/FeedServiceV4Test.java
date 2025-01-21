package com.demo.feedsystemdesign.feed.service.v4;

import com.demo.feedsystemdesign.follow.service.FollowServiceV2;
import com.demo.feedsystemdesign.post.domain.Post;
import com.demo.feedsystemdesign.post.domain.PostRepository;
import com.demo.feedsystemdesign.post.service.v3.event.PostCreatedEvent;
import com.demo.feedsystemdesign.support.general.RedisTest;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;

@RedisTest
class FeedServiceV4Test {

    @Autowired
    private FeedServiceV4 feedService;

    @Autowired
    private FollowServiceV2 followService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void 게시물이_작성되면_작성자_팔로워들의_피드에_해당_게시물을_삽입한다() {
        User user = userRepository.save(new User());
        User follower = userRepository.save(new User());
        followService.follow(follower.getId(), user.getId());
        Post post = postRepository.save(new Post(user.getId(), "test content"));

        runThrowingCheckedException(() ->
                feedService.onPostCreated(new PostCreatedEvent(user.getId(), post.getId()))
                        .get()
        );

        assertThat(feedService.getFeed(follower.getId()).getPosts())
                .map(Post::getId)
                .containsExactly(post.getId());
    }

    private void runThrowingCheckedException(Callable<?> callable) {
        try {
            callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}