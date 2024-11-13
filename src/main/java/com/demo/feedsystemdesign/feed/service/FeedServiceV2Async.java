package com.demo.feedsystemdesign.feed.service;

import com.demo.feedsystemdesign.feed.domain.Feed;
import com.demo.feedsystemdesign.feed.domain.FeedPost;
import com.demo.feedsystemdesign.feed.domain.FeedPostRepository;
import com.demo.feedsystemdesign.follow.service.FollowService;
import com.demo.feedsystemdesign.post.event.PostCreatedEvent;
import com.demo.feedsystemdesign.post.service.PostServiceV2Async;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@Service
public class FeedServiceV2Async {

    private final PostServiceV2Async postService;
    private final FeedPostRepository feedPostRepository;
    private final FollowService followService;

    public Feed getFeed(Long userId) {
        List<FeedPost> feedPosts = feedPostRepository.findAllByUserId(userId);

        List<Long> postIds = feedPosts.stream().map(FeedPost::getPostId).toList();

        return new Feed(postService.getPostsBy(postIds));
    }

    @Async
    @EventListener
    public Future<Void> onPostCreated(PostCreatedEvent event) {
        Long userId = event.getUserId();
        for (Long followerId : followService.getFollowers(userId)) {
            feedPostRepository.save(new FeedPost(followerId, event.getPostId()));
        }
        return CompletableFuture.completedFuture(null);
    }
}
