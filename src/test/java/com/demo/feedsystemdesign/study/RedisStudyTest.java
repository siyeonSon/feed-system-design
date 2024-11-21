package com.demo.feedsystemdesign.study;

import com.demo.feedsystemdesign.support.general.RedisTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RedisTest
public class RedisStudyTest {

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    @Nested
    class SetTest {
        @Test
        void 키의_존재_여부를_확인할_수_있다() {
            Boolean hasKey = redisTemplate.opsForSet().getOperations().hasKey("1234");
            assertThat(hasKey).isFalse();
        }

        @Test
        void 키가_없으면_빈_배열을_반환한다() {
            Set<Long> values = redisTemplate.opsForSet().members("1234");

            assertThat(values).isNotNull();
            assertThat(values).isEmpty();
        }

        @Test
        void 한번에_여러_개의_값을_추가할_수_있다() {
            Long count = redisTemplate.opsForSet().add("134", 1L, 2L, 3L, 4L, 5L);

            assertThat(count).isEqualTo(5);
        }
    }

    @Nested
    class ListTest {
        @Test
        void 키의_존재_여부를_확인할_수_있다() {
            Boolean hasKey = redisTemplate.opsForList().getOperations().hasKey("1234");
            assertThat(hasKey).isFalse();
        }

        @Test
        void 키가_없으면_빈_배열을_반환한다() {
            List<Long> values = redisTemplate.opsForList().range("1234", 0, -1);

            assertThat(values).isNotNull();
            assertThat(values).isEmpty();
        }
    }
}
