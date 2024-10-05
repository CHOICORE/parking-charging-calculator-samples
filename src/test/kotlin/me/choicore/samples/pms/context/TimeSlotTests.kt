package me.choicore.samples.pms.context

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalTime

class TimeSlotTests {
    @Test
    @DisplayName("하루 전체 시간을 의미하는 companion object")
    fun t1() {
        val timeSlot: TimeSlot = TimeSlot.FULL_TIME
        assertThat(timeSlot.isFullTime).isTrue()
        assertThat(timeSlot.startTimeInclusive).isEqualTo(LocalTime.MIN)
        assertThat(timeSlot.endTimeExclusive).isEqualTo(LocalTime.MAX)
        assertThat(timeSlot.totalMinutes).isEqualTo(TimeSlot.FULL_DAY_OF_MINUTES)
    }

    @Test
    @DisplayName("시작과 종료 시간이 같을 수 없다.")
    fun t2() {
        val same = LocalTime.now()
        assertThatThrownBy {
            TimeSlot(same, same)
        }
    }

    @Test
    @DisplayName("시작 시간이 종료 시간 이후인 경우 자정이 지난 것으로 판단한다.")
    fun t3() {
        val timeSlot = TimeSlot(LocalTime.now(), LocalTime.now().minusHours(1))
        assertThat(timeSlot.crossesMidnight).isTrue()
        assertThat(timeSlot.totalMinutes).isLessThan(TimeSlot.FULL_DAY_OF_MINUTES)
    }

    @Test
    @DisplayName("시작 시간이 종료 시간 이전인 경우")
    fun t4() {
        val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(10, 0))
        assertThat(timeSlot.crossesMidnight).isFalse()
        assertThat(timeSlot.contains(LocalTime.of(8, 59))).isFalse()
        assertThat(timeSlot.contains(LocalTime.of(9, 0))).isTrue()
        assertThat(timeSlot.contains(LocalTime.of(9, 30))).isTrue()
        assertThat(timeSlot.contains(LocalTime.of(10, 0))).isTrue()
        assertThat(timeSlot.contains(LocalTime.of(10, 1))).isFalse()
    }

    @Test
    @DisplayName("시작 시간과 종료시간이 자정을 넘어가는 경우")
    fun t5() {
        val timeSlot = TimeSlot(LocalTime.of(23, 0), LocalTime.of(1, 0))
        assertThat(timeSlot.crossesMidnight).isTrue()
        assertThat(timeSlot.contains(LocalTime.of(22, 59))).isFalse()
        assertThat(timeSlot.contains(LocalTime.of(23, 0))).isTrue()
        assertThat(timeSlot.contains(LocalTime.of(0, 0))).isTrue()
        assertThat(timeSlot.contains(LocalTime.of(1, 0))).isTrue()
        assertThat(timeSlot.contains(LocalTime.of(1, 1))).isFalse()
    }
}
