package com.demo.feedsystemdesign.post.service.v4;

import com.demo.feedsystemdesign.common.exception.ErrorCode;
import com.demo.feedsystemdesign.common.exception.NotFoundException;
import com.demo.feedsystemdesign.post.domain.PostRepository;
import com.demo.feedsystemdesign.post.service.dto.PostResponse;
import com.demo.feedsystemdesign.support.general.ServiceTest;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ServiceTest
class PostServiceV4Test {

    @Autowired
    private PostServiceV4 postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void 존재하는_사용자만_게시물을_작성할_수_있다() {
        assertThatThrownBy(() -> postService.createPost(0L, ""))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    void 사용자의_게시물을_작성한다() {
        User user = new User();
        User saved = userRepository.save(user);
        String content = "test content";

        PostResponse post = postService.createPost(saved.getId(), content);

        assertThat(postRepository.findById(post.postId())).isPresent();
    }
}