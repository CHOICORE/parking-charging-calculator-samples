package me.choicore.samples.pms.context

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalTime

class TimebaseTests {
    @Test
    @DisplayName("하루 전체 시간을 의미하는 companion object")
    fun t1() {
        val timebase: Timebase = Timebase.FULL_TIME
        assertThat(timebase.isFullTime).isTrue()
        assertThat(timebase.from).isEqualTo(LocalTime.MIN)
        assertThat(timebase.to).isEqualTo(LocalTime.MAX)
        assertThat(timebase.duration()).isEqualTo(Timebase.FULL_DAY_OF_MINUTES)
    }

    @Test
    @DisplayName("시작과 끝 시간이 같은 경우 24시간을 의미한다.")
    fun t2() {
        val same = LocalTime.now()
        val timebase = Timebase(same, same)
        assertThat(timebase.crossesMidnight).isTrue()
        assertThat(timebase.duration()).isEqualTo(1440)
        assertThat(timebase.isFullTime).isTrue()
    }

    @Test
    @DisplayName("시작 시간이 끝 시간보다 늦은 경우 자정이 지난 것으로 판단한다.")
    fun t3() {
        val timebase = Timebase(LocalTime.now(), LocalTime.now().minusHours(1))
        assertThat(timebase.crossesMidnight).isTrue()
        assertThat(timebase.duration()).isLessThan(Timebase.FULL_DAY_OF_MINUTES)
    }
}