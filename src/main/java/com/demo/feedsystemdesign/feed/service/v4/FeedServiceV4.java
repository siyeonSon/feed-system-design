package com.demo.feedsystemdesign.feed.service.v4;

import com.demo.feedsystemdesign.feed.cache.FeedCache;
import com.demo.feedsystemdesign.feed.domain.Feed;
import com.demo.feedsystemdesign.feed.domain.FeedPost;
import com.demo.feedsystemdesign.feed.domain.FeedPostRepository;
import com.demo.feedsystemdesign.follow.service.FollowService;
import com.demo.feedsystemdesign.post.service.v3.event.PostCreatedEvent;
import com.demo.feedsystemdesign.post.service.v4.PostServiceV4;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@Service
public class FeedServiceV4 {

    private final PostServiceV4 postService;
    private final FeedPostRepository feedPostRepository;
    private final FollowService followService;
    private final FeedCache feedCache;

    @Transactional(readOnly = true)
    public Feed getFeed(Long userId) {
        if (feedCache.notHas(userId)) {
            List<Long> postIds = feedPostRepository.findAllByUserId(userId)
                    .stream()
                    .map(FeedPost::getPostId)
                    .toList();
            // TODO: DB에서 가져온 데이터를 기왕 가져온 김에 캐시에도 저장해야 하지 않을까?
            //   추천 시스템에서는 캐시에 저장하는 게 맞지만, 팔로워들의 최신 피드를 보여주는 것이 목적이라면 저장할 필요가 없음
            return new Feed(postService.getPostsBy(postIds));
        }
        List<Long> postIds = feedCache.getBy(userId);
        return new Feed(postService.getPostsBy(postIds));
    }

    @Async
    @EventListener
    public Future<Void> onPostCreated(PostCreatedEvent event) {
        Long userId = event.getUserId();
        for (Long followerId : followService.getFollowers(userId)) {
            feedCache.add(followerId, event.getPostId());
        }
        return CompletableFuture.completedFuture(null);
    }
}
