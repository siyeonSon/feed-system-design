package com.demo.feedsystemdesign.follow.domain;

import com.demo.feedsystemdesign.common.exception.FollowException;
import com.demo.feedsystemdesign.common.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FollowersTest {

    @Test
    void 다른_사용자가_팔로우_할_수_있다() {
        Followers followers = new Followers(1L);

        followers.add(2L);

        assertThat(followers.contains(2L)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"2, true", "0, false"})
    void 팔로우_여부를_알_수_있다(Long otherUserId, boolean follows) {
        Followers followers = new Followers(1L);

        followers.add(2L);

        assertThat(followers.contains(otherUserId)).isEqualTo(follows);
    }

    @Test
    void 팔로워들을_알_수_있다() {
        Followers followers = new Followers(1L);
        followers.add(2L);
        followers.add(3L);
        followers.add(4L);

        assertThat(followers.findAll()).contains(2L, 3L, 4L);
    }

    @Test
    void 자신을_팔로우_할_수_없다() {
        Followers followers = new Followers(1L);

        assertThatThrownBy(() -> followers.add(1L))
                .isInstanceOf(FollowException.class)
                .hasMessage(ErrorCode.SELF_FOLLOWING.getMessage());
    }
}
