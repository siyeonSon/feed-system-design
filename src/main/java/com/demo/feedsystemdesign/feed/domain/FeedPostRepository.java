package com.demo.feedsystemdesign.feed.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedPostRepository extends JpaRepository<FeedPost, Long> {
    List<FeedPost> findAllByUserId(Long userId);
}
