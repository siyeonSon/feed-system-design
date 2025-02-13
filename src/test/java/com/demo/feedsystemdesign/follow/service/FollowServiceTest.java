package com.demo.feedsystemdesign.follow.service;

import com.demo.feedsystemdesign.common.exception.ErrorCode;
import com.demo.feedsystemdesign.common.exception.NotFoundException;
import com.demo.feedsystemdesign.support.general.ServiceTest;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

@ServiceTest
class FollowServiceTest {

    @Autowired
    private FollowService followService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void 다른_사용자를_팔로우_할_수_있다() {
        User user = userRepository.save(new User());
        User subject = userRepository.save(new User());

        followService.follow(user.getId(), subject.getId());

        assertThat(followService.getFollowers(subject.getId()))
                .containsExactly(user.getId());
    }

    @Test
    void 어떤_사용자의_팔로워들을_알_수_있다() {
        User user = userRepository.save(new User());
        User subject = userRepository.save(new User());
        User otherSubject = userRepository.save(new User());

        followService.follow(subject.getId(), user.getId());
        followService.follow(otherSubject.getId(), user.getId());

        assertThat(followService.getFollowers(user.getId()))
                .containsExactly(subject.getId(), otherSubject.getId());
    }

    @Test
    void 팔로우_대상이_없으면_예외가_발생한다() {
        User user = userRepository.save(new User());

        assertThatThrownBy(() -> followService.follow(user.getId(), 0L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    void 팔로우_주체가_없으면_예외가_발생한다() {
        User user = userRepository.save(new User());

        assertThatThrownBy(() -> followService.follow(0L, user.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    void 사용자가_팔로우_하고_있는_사용자들을_반환한다() {
        User user = userRepository.save(new User());
        User follower = userRepository.save(new User());
        User otherFollower = userRepository.save(new User());

        followService.follow(user.getId(), follower.getId());
        followService.follow(user.getId(), otherFollower.getId());

        assertThat(followService.getFollowings(user.getId()))
                .containsExactly(follower.getId(), otherFollower.getId());
    }

}
