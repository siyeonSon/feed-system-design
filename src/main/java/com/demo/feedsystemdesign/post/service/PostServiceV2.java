package com.demo.feedsystemdesign.post.service;

import com.demo.feedsystemdesign.common.exception.NotFoundException;
import com.demo.feedsystemdesign.follow.service.FollowService;
import com.demo.feedsystemdesign.post.domain.Post;
import com.demo.feedsystemdesign.post.domain.PostRepository;
import com.demo.feedsystemdesign.post.service.dto.PostResponse;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.demo.feedsystemdesign.common.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostServiceV2 {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowService followService;
    private final FeedInsertion feedInsertion;

    @Transactional
    public PostResponse createPost(Long userId, String content) {
        validate(userId);
        Post post = new Post(userId, content);
        Post saved = postRepository.save(post);

        for (Long followerId : followService.getFollowers(userId)) {
            feedInsertion.insert(followerId, post.getId());
        }

        return PostResponse.of(saved);
    }

    private void validate(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public List<Post> getPostsBy(List<Long> postIds) {
        return postRepository.findAllById(postIds);
    }
}
