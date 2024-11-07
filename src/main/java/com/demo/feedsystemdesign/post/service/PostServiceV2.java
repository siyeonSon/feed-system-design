package com.demo.feedsystemdesign.post.service;

import com.demo.feedsystemdesign.post.domain.Post;
import com.demo.feedsystemdesign.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceV2 {

    private final PostRepository postRepository;

    public List<Post> getPostsBy(List<Long> postIds) {
        return postRepository.findAllById(postIds);
    }
}
