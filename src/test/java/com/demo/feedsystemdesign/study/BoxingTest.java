package com.demo.feedsystemdesign.study;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BoxingTest {

    @Test
    void 박싱이_없는_경우가_가장_빠르며_박싱_및_언박싱이_거듭될수록_느려진다() {
        Duration neverBox = time(() -> {
            long a = 0L;
            for (long i = 0; i < 100_000; i++) {
                a = a + i;  // 박싱과 언박싱 없음
            }
        });
        Duration unboxAndBox = time(() -> {
            Long a = 0L;
            for (long i = 0; i < 100_000; i++) {
                a = a + i;  // 더하는 연산에서 언박싱 발생 -> a 대입 과정에서 박싱 발생
            }
        });
        Duration unboxBothThenBox = time(() -> {
            Long a = 0L;
            for (Long i = 0L; i < 100_000; i++) {
                a = a + i;  // 더하는 연산에서 두 변수 모두 언박싱 발생 -> a 대입 과정에서 박싱 발생
            }
        });

        assertThat(neverBox).isLessThan(unboxAndBox);
        assertThat(unboxAndBox).isLessThan(unboxBothThenBox);
    }

    @Test
    void 가장_느린_경우와_가장_빠른_경우에도_유의미한_차이는_없다() {
        Duration operationsWithoutBoxing = time(() -> {
            long a = 0L;
            for (long i = 0; i < 100_000; i++) {
                a = a + i;
            }
        });
        Duration operationsWithUnboxingAndBoxing = time(() -> {
            Long a = 0L;
            for (Long i = 0L; i < 100_000; i++) {
                a = a + i;
            }
        });

        assertThat(operationsWithoutBoxing).isCloseTo(operationsWithUnboxingAndBoxing, Duration.ofMillis(10));
    }


    private Duration time(Runnable runnable) {
        Instant start = Instant.now();
        runnable.run();
        Instant end = Instant.now();
        return Duration.between(start, end);
    }
}
