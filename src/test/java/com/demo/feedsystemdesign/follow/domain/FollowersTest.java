package com.demo.feedsystemdesign.follow.domain;

import com.demo.feedsystemdesign.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FollowersTest {

    @Test
    void 다른_사용자가_팔로우_할_수_있다() {
        Followers followers = new Followers();
        User other = new User();

        followers.add(other.getId());

        assertThat(followers.contains(other.getId())).isTrue();
    }

    @Test
    void 팔로워들을_알_수_있다() {
        Followers followers = new Followers();
        followers.add(1L);
        followers.add(2L);
        followers.add(3L);

        assertThat(followers.findAll()).contains(1L, 2L, 3L);
    }
}
