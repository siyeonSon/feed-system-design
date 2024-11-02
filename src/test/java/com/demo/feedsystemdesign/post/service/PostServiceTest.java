package com.demo.feedsystemdesign.post.service;

import com.demo.feedsystemdesign.common.exception.ErrorCode;
import com.demo.feedsystemdesign.common.exception.NotFoundException;
import com.demo.feedsystemdesign.post.service.dto.PostResponse;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

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

        assertThat(post.content()).isEqualTo(content);
    }

    @Test
    void 사용자의_모든_게시물을_조회한다() {
        User user = userRepository.save(new User());

        PostResponse post = postService.createPost(user.getId(), "test content");

        assertThat(postService.getPosts(user.getId()))
                .singleElement()
                .satisfies(result -> {
                    assertThat(result.getContent()).isEqualTo(post.content());
                    assertThat(result.getUserId()).isEqualTo(user.getId());
                });
    }

}