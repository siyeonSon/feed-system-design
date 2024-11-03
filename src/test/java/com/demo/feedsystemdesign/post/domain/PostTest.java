package com.demo.feedsystemdesign.post.domain;

import com.demo.feedsystemdesign.common.exception.ErrorCode;
import com.demo.feedsystemdesign.common.exception.PostContentLengthException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    void 게시물_내용은_한_글자_이상이어야_한다() {
        Assertions.assertThatThrownBy(() -> new Post(1L, ""))
                .isInstanceOf(PostContentLengthException.class)
                .hasMessage(ErrorCode.POST_CONTENT_TOO_SHORT.getMessage());
    }

    @Test
    void 게시물_내용은_200자_미만이어야_한다() {
        String content = "a".repeat(201);

        Assertions.assertThatThrownBy(() -> new Post(1L, content))
                .isInstanceOf(PostContentLengthException.class)
                .hasMessage(ErrorCode.POST_CONTENT_TOO_LONG.getMessage());
    }
}