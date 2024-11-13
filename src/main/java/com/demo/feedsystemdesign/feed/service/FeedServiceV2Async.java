package com.demo.feedsystemdesign.feed.service;

import com.demo.feedsystemdesign.feed.domain.Feed;
import com.demo.feedsystemdesign.feed.domain.FeedPost;
import com.demo.feedsystemdesign.feed.domain.FeedPostRepository;
import com.demo.feedsystemdesign.follow.service.FollowService;
import com.demo.feedsystemdesign.post.event.PostCreatedEvent;
import com.demo.feedsystemdesign.post.service.PostServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedServiceV2Async {

    private final PostServiceV2 postService;
    private final FeedPostRepository feedPostRepository;
    private final FollowService followService;

    public Feed getFeed(Long userId) {
        List<FeedPost> feedPosts = feedPostRepository.findAllByUserId(userId);

        List<Long> postIds = feedPosts.stream().map(FeedPost::getPostId).toList();

        return new Feed(postService.getPostsBy(postIds));
    }

    @EventListener
    public void onPostCreated(PostCreatedEvent event) {
        Long userId = event.getUserId();
        for (Long followerId : followService.getFollowers(userId)) {
            feedPostRepository.save(new FeedPost(followerId, event.getPostId()));
        }
    }
}
