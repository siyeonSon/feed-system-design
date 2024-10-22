package com.demo.feedsystemdesign.follow.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FollowServiceTest {

    @Autowired
    private FollowService followService;

    @Test
    void 다른_사용자를_팔로우_할_수_있다() {
        Long userId = 1L;
        Long subjectId = 2L;

        followService.follow(userId, subjectId);

        assertThat(followService.getFollowers(subjectId)).contains(userId);
    }
}
