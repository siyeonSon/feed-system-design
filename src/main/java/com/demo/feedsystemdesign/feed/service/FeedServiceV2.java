package com.demo.feedsystemdesign.feed.service;

import com.demo.feedsystemdesign.feed.domain.Feed;
import com.demo.feedsystemdesign.feed.domain.FeedPost;
import com.demo.feedsystemdesign.feed.domain.FeedPostRepository;
import com.demo.feedsystemdesign.post.service.PostServiceV2;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedServiceV2 {

    private final PostServiceV2 postService;
    private final FeedPostRepository feedPostRepository;

    public Feed getFeed(Long userId) {
        List<FeedPost> feedPosts = feedPostRepository.findAllByUserId(userId);

        List<Long> postIds = feedPosts.stream().map(FeedPost::getPostId).toList();

        return new Feed(postService.getPostsBy(postIds));
    }

}
