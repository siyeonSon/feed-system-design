package com.demo.feedsystemdesign.feed.service.v2;

import com.demo.feedsystemdesign.feed.domain.FeedPost;
import com.demo.feedsystemdesign.feed.domain.FeedPostRepository;
import com.demo.feedsystemdesign.post.service.v2.FeedInsertion;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class FeedPostInsertion implements FeedInsertion {
    private final FeedPostRepository feedPostRepository;

    public FeedPostInsertion(FeedPostRepository feedPostRepository) {
        this.feedPostRepository = feedPostRepository;
    }

    @Transactional
    @Override
    public void insert(Long userId, Long postId) {
        FeedPost feedPost = new FeedPost(userId, postId);
        feedPostRepository.save(feedPost);
    }
}
