package com.demo.feedsystemdesign.study;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class BoxingTest {

    @Test
    void 십만개를_박싱_및_언박싱한다() {
        Duration unboxingAndBoxing = time(() -> {
            Long a = 0L;
            for (long i = 0; i < 100_000; i++) {
                a = a + i;  // 더하는 연산에서 언박싱 발생 -> a 대입 과정에서 박싱 발생
            }
        });
        Duration noBoxing = time(() -> {
            long a = 0L;
            for (long i = 0; i < 100_000; i++) {
                a = a + i;  // 박싱과 언박싱 없음
            }
        });
        Duration unboxingBothAndBoxing = time(() -> {
            Long a = 0L;
            for (Long i = 0L; i < 100_000; i++) {
                a = a + i;  // 더하는 연산에서 두 변수 모두 언박싱 발생 -> a 대입 과정에서 박싱 발생
            }
        });

        assertThat(noBoxing).isLessThan(unboxingAndBoxing);
        assertThat(unboxingAndBoxing).isLessThan(unboxingBothAndBoxing);

    }


    private Duration time(Runnable runnable) {
        Instant start = Instant.now();
        runnable.run();
        Instant end = Instant.now();
        return Duration.between(start, end);
    }
}
