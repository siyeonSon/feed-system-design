package com.demo.feedsystemdesign.follow.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class FollowersTest {

    @Test
    void 다른_사용자가_팔로우_할_수_있다() {
        Followers followers = new Followers();

        followers.add(1L);

        assertThat(followers.contains(1L)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"1, true", "0, false"})
    void 팔로우_여부를_알_수_있다(Long otherUserId, boolean follows) {
        Followers followers = new Followers();

        followers.add(1L);

        assertThat(followers.contains(otherUserId)).isEqualTo(follows);
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
