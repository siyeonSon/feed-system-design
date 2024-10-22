package com.demo.feedsystemdesign.follow.domain;

import com.demo.feedsystemdesign.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FollowersTest {

    @Test
    void 다른_사용자가_팔로우_할_수_있다() {
        Followers followers = new Followers();
        User user = new User();

        followers.add(user.getId());

        assertThat(followers.contains(user.getId())).isTrue();
    }
}
