package com.demo.feedsystemdesign.follow.service;

import com.demo.feedsystemdesign.common.exception.ErrorCode;
import com.demo.feedsystemdesign.common.exception.NotFoundException;
import com.demo.feedsystemdesign.user.domain.User;
import com.demo.feedsystemdesign.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class FollowServiceTest {

    @Autowired
    private FollowService followService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void 다른_사용자를_팔로우_할_수_있다() {
        Long userId = 1L;
        Long subjectId = 2L;

        followService.follow(userId, subjectId);

        assertThat(followService.getFollowers(subjectId)).contains(userId);
    }

    @Test
    void 어떤_사용자의_팔로워들을_알_수_있다() {
        userRepository.save(new User());
        userRepository.save(new User());
        userRepository.save(new User());

        followService.follow(2L, 1L);
        followService.follow(3L, 1L);

        assertThat(followService.getFollowers(1L)).contains(2L, 3L);
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
}
