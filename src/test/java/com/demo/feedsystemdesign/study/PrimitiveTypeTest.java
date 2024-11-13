package com.demo.feedsystemdesign.study;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PrimitiveTypeTest {

    @Test
    void long은_지역_내에서_값을_할당하지_않으면_컴파일_에러가_발생한다() {
        long a;

//        System.out.println("a = " + a); // 에러 발생
    }

    @Test
    void 그러나_객체의_필드로_long을_사용하면_0으로_초기화된다() {
        class PrimitiveLongClass {
            long a;
        }

        PrimitiveLongClass testInstance = new PrimitiveLongClass();

        assertThat(testInstance.a).isZero();
        // 0으로 초기화되는 것은 의도와 다르다.
        // 더 정확한 제어를 위해서는 null이 들어가서 오류가 발생하는 편이 낫다.
    }

    @Test
    void 따라서_0을_기본값으로_사용하고_싶은_것이_아니라면_Long이_더_안전할수도_있다() {
        class ReferenceLongClass {
            Long a;
        }

        ReferenceLongClass testInstance = new ReferenceLongClass();

        assertThat(testInstance.a).isNull();
    }
}
