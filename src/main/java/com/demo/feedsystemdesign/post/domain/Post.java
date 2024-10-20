package com.demo.feedsystemdesign.post.domain;

import com.demo.feedsystemdesign.common.BaseTimeEntity;
import com.demo.feedsystemdesign.common.exception.PostContentLengthException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.demo.feedsystemdesign.common.exception.ErrorCode.POST_CONTENT_TOO_LONG;
import static com.demo.feedsystemdesign.common.exception.ErrorCode.POST_CONTENT_TOO_SHORT;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long userId;

    public Post(Long userId, String content) {
        validateContent(content);
        this.userId = userId;
        this.content = content;
    }

    private void validateContent(String content) {
        if (content.length() < 1) {
            throw new PostContentLengthException(POST_CONTENT_TOO_SHORT);
        }
        if (content.length() >= 200) {
            throw new PostContentLengthException(POST_CONTENT_TOO_LONG);
        }
    }

}
