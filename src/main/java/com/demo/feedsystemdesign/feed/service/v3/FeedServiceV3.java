package com.demo.feedsystemdesign.feed.service.v3;

import com.demo.feedsystemdesign.feed.domain.Feed;
import com.demo.feedsystemdesign.feed.domain.FeedPost;
import com.demo.feedsystemdesign.feed.domain.FeedPostRepository;
import com.demo.feedsystemdesign.follow.service.FollowService;
import com.demo.feedsystemdesign.post.service.v3.PostServiceV3;
import com.demo.feedsystemdesign.post.service.v3.event.PostCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@Service
public class FeedServiceV3 {

    private final PostServiceV3 postService;
    private final FeedPostRepository feedPostRepository;
    private final FollowService followService;

    public Feed getFeed(Long userId) {
        List<FeedPost> feedPosts = feedPostRepository.findAllByUserId(userId);

        List<Long> postIds = feedPosts.stream().map(FeedPost::getPostId).toList();

        return Feed.of(postService.getPostsBy(postIds));
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
