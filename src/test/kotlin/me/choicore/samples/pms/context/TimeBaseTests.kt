package me.choicore.samples.pms.context

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalTime

class TimeBaseTests {
    @Test
    @DisplayName("하루 전체 시간을 의미하는 companion object")
    fun t1() {
        val timeBase: TimeBase = TimeBase.FULL_TIME
        assertThat(timeBase.isFullTime).isTrue()
        assertThat(timeBase.from).isEqualTo(LocalTime.MIN)
        assertThat(timeBase.to).isEqualTo(LocalTime.MAX)
        assertThat(timeBase.duration()).isEqualTo(TimeBase.FULL_DAY_OF_MINUTES)
    }

    @Test
    @DisplayName("시작과 끝 시간이 같은 경우 24시간을 의미한다.")
    fun t2() {
        val same = LocalTime.now()
        val timeBase = TimeBase(same, same)
        assertThat(timeBase.crossesMidnight).isTrue()
        assertThat(timeBase.duration()).isEqualTo(1440)
        assertThat(timeBase.isFullTime).isTrue()
    }

    @Test
    @DisplayName("시작 시간이 끝 시간보다 늦은 경우 자정이 지난 것으로 판단한다.")
    fun t3() {
        val timeBase = TimeBase(LocalTime.now(), LocalTime.now().minusHours(1))
        assertThat(timeBase.crossesMidnight).isTrue()
        assertThat(timeBase.duration()).isLessThan(TimeBase.FULL_DAY_OF_MINUTES)
    }

    @Test
    @DisplayName("시작 시간이 끝 시간보다 빠른 경우 포함 여부 확인")
    fun t4() {
        val timeBase = TimeBase(LocalTime.of(9, 0), LocalTime.of(10, 0))
        assertThat(timeBase.crossesMidnight).isFalse()
        assertThat(timeBase.contains(LocalTime.of(8, 59))).isFalse()
        assertThat(timeBase.contains(LocalTime.of(9, 0))).isTrue()
        assertThat(timeBase.contains(LocalTime.of(9, 30))).isTrue()
        assertThat(timeBase.contains(LocalTime.of(10, 0))).isTrue()
        assertThat(timeBase.contains(LocalTime.of(10, 1))).isFalse()
    }

    @Test
    @DisplayName("시작 시간이 끝 시간보다 늦은 경우 포함 여부 확인")
    fun t5() {
        val timeBase = TimeBase(LocalTime.of(23, 0), LocalTime.of(1, 0))
        assertThat(timeBase.crossesMidnight).isTrue()
        assertThat(timeBase.contains(LocalTime.of(22, 59))).isFalse()
        assertThat(timeBase.contains(LocalTime.of(23, 0))).isTrue()
        assertThat(timeBase.contains(LocalTime.of(0, 0))).isTrue()
        assertThat(timeBase.contains(LocalTime.of(1, 0))).isTrue()
        assertThat(timeBase.contains(LocalTime.of(1, 1))).isFalse()
    }
}
