package com.demo.feedsystemdesign.post.service.v4;

import com.demo.feedsystemdesign.common.exception.ErrorCode;
import com.demo.feedsystemdesign.common.exception.NotFoundException;
import com.demo.feedsystemdesign.post.cache.AsyncPostCaching;
import com.demo.feedsystemdesign.post.cache.PostCache;
import com.demo.feedsystemdesign.post.domain.Post;
import com.demo.feedsystemdesign.post.domain.PostRepository;
import com.demo.feedsystemdesign.post.service.dto.PostResponse;
import com.demo.feedsystemdesign.post.service.v3.event.PostCreatedEvent;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.demo.feedsystemdesign.common.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostServiceV4 {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final AsyncPostCaching asyncPostCaching;
    private final PostCache postCache;

    @Transactional
    public PostResponse createPost(Long userId, String content) {
        validate(userId);
        Post post = new Post(userId, content);
        Post saved = postRepository.save(post);

        asyncPostCaching.cache(post);
        eventPublisher.publishEvent(new PostCreatedEvent(userId, post.getId()));

        return PostResponse.of(saved);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBy(List<Long> postIds) {
        return postIds.stream()
                .map(it -> {
                    if (postCache.contains(it)) {
                        return PostResponse.of(postCache.getById(it));
                    }
                    return postRepository.findById(it)
                            .map(PostResponse::of)
                            .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
                })
                .toList();
    }

    private void validate(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

}
