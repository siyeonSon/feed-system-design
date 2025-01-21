package com.demo.feedsystemdesign.feed.service.v3;

import com.demo.feedsystemdesign.follow.service.FollowService;
import com.demo.feedsystemdesign.post.domain.Post;
import com.demo.feedsystemdesign.post.domain.PostRepository;
import com.demo.feedsystemdesign.post.service.dto.PostResponse;
import com.demo.feedsystemdesign.post.service.v3.event.PostCreatedEvent;
import com.demo.feedsystemdesign.support.general.ServiceTest;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
class FeedServiceV3Test {

    @Autowired
    private FeedServiceV3 feedService;

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

        try {
            feedService.onPostCreated(new PostCreatedEvent(user.getId(), post.getId()))
                    .get();
        } catch (Exception ignored) {
        }

        assertThat(feedService.getFeed(follower.getId()).getPosts())
                .map(PostResponse::postId)
                .containsExactly(post.getId());
    }

}