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
        assertThat(timeSlot.endTimeInclusive).isEqualTo(LocalTime.MAX)
        assertThat(timeSlot.totalMinutes).isEqualTo(TimeSlot.FULL_DAY_OF_MINUTES)
    }

    @Test
    @DisplayName("시작과 종료 시간은 같을 수 없다.")
    fun t2() {
        val same = LocalTime.now()
        assertThatThrownBy {
            TimeSlot(same, same)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("시작 시간과 종료 시간이 같을 수 없습니다.")
    }

    @Test
    @DisplayName("시작 시간이 종료 시간 이후인 경우 자정이 지난 것으로 판단한다.")
    fun t3() {
        val timeSlot = TimeSlot(startTimeInclusive = LocalTime.now(), endTimeInclusive = LocalTime.now().minusHours(1))
        assertThat(timeSlot.crossesMidnight).isTrue()
        assertThat(timeSlot.totalMinutes).isLessThan(TimeSlot.FULL_DAY_OF_MINUTES)
    }

    @Test
    @DisplayName("자정을 넘어가지 않는 TimeSlot 에 포함되는 시간인지 확인")
    fun t4() {
        val timeSlot = TimeSlot(startTimeInclusive = LocalTime.of(9, 0), endTimeInclusive = LocalTime.of(10, 0))
        assertThat(timeSlot.crossesMidnight).isFalse()
        assertThat(LocalTime.of(8, 59) in timeSlot).isFalse()
        assertThat(LocalTime.of(9, 0) in timeSlot).isTrue()
        assertThat(LocalTime.of(9, 30) in timeSlot).isTrue()
        assertThat(LocalTime.of(10, 0) in timeSlot).isTrue()
        assertThat(LocalTime.of(10, 1) in timeSlot).isFalse()
    }

    @Test
    @DisplayName("자정을 넘어가는 TimeSlot 에 포함되는 시간인지 확인")
    fun t5() {
        val timeSlot = TimeSlot(startTimeInclusive = LocalTime.of(23, 0), endTimeInclusive = LocalTime.of(1, 0))
        assertThat(timeSlot.crossesMidnight).isTrue()
        assertThat(LocalTime.of(22, 59) in timeSlot).isFalse()
        assertThat(LocalTime.of(22, 59) in timeSlot).isFalse()
        assertThat(LocalTime.of(23, 0) in timeSlot).isTrue()
        assertThat(LocalTime.of(0, 0) in timeSlot).isTrue()
        assertThat(LocalTime.of(1, 0) in timeSlot).isTrue()
        assertThat(LocalTime.of(1, 1) in timeSlot).isFalse()
    }

    @Test
    @DisplayName("overlap true cases, other.startTimeInclusive is before this.endTimeInclusive")
    fun t6() {
        val timeSlot1 = TimeSlot(startTimeInclusive = LocalTime.of(9, 0), endTimeInclusive = LocalTime.of(10, 0))
        val timeSlot2 = TimeSlot(startTimeInclusive = LocalTime.of(9, 59), endTimeInclusive = LocalTime.of(11, 0))
        assertThat(timeSlot1.isOverlap(timeSlot2)).isTrue()
        assertThat(timeSlot2.isOverlap(timeSlot1)).isTrue()
    }

    @Test
    @DisplayName("overlap false cases, other.startTimeInclusive is equal or after this.endTimeInclusive")
    fun t7() {
        val timeSlot1 = TimeSlot(startTimeInclusive = LocalTime.of(9, 0), endTimeInclusive = LocalTime.of(10, 0))
        val timeSlot2 = TimeSlot(startTimeInclusive = LocalTime.of(10, 0), endTimeInclusive = LocalTime.of(11, 0))
        assertThat(timeSlot1.isOverlap(timeSlot2)).isFalse()
        assertThat(timeSlot2.isOverlap(timeSlot1)).isFalse()
        val timeSlot3 = TimeSlot(startTimeInclusive = LocalTime.of(10, 1), endTimeInclusive = LocalTime.of(11, 0))
        assertThat(timeSlot1.isOverlap(timeSlot3)).isFalse()
        assertThat(timeSlot3.isOverlap(timeSlot1)).isFalse()
    }

    @Test
    @DisplayName("overlap true cases, this.crossesMidnight is false && other.crossesMidnight is true")
    fun t8() {
        val timeSlot1 = TimeSlot(startTimeInclusive = LocalTime.of(9, 0), endTimeInclusive = LocalTime.of(18, 0))
        val timeSlot2 = TimeSlot(startTimeInclusive = LocalTime.of(17, 59), endTimeInclusive = LocalTime.of(9, 0))
        assertThat(timeSlot1.isOverlap(timeSlot2)).isTrue()
        assertThat(timeSlot2.isOverlap(timeSlot1)).isTrue()
    }

    @Test
    @DisplayName("overlap false cases, this.crossesMidnight is true && other.crossesMidnight is false")
    fun t9() {
        val timeSlot1 = TimeSlot(startTimeInclusive = LocalTime.of(9, 0), endTimeInclusive = LocalTime.of(18, 0))
        val timeSlot2 = TimeSlot(startTimeInclusive = LocalTime.of(18, 0), endTimeInclusive = LocalTime.of(9, 0))
        assertThat(timeSlot1.isOverlap(timeSlot2)).isFalse()
        assertThat(timeSlot2.isOverlap(timeSlot1)).isFalse()
        val timeSlot3 = TimeSlot(startTimeInclusive = LocalTime.of(18, 1), endTimeInclusive = LocalTime.of(9, 0))
        assertThat(timeSlot1.isOverlap(timeSlot3)).isFalse()
        assertThat(timeSlot3.isOverlap(timeSlot1)).isFalse()
    }

    @Test
    @DisplayName("overlap true cases, this.crossesMidnight is true && other.crossesMidnight is false")
    fun t10() {
        val timeSlot1 = TimeSlot(startTimeInclusive = LocalTime.of(18, 0), endTimeInclusive = LocalTime.of(9, 0))
        val timeSlot2 = TimeSlot(startTimeInclusive = LocalTime.of(8, 59), endTimeInclusive = LocalTime.of(18, 0))
        assertThat(timeSlot1.isOverlap(timeSlot2)).isTrue()
        assertThat(timeSlot2.isOverlap(timeSlot1)).isTrue()
    }

    @Test
    @DisplayName("overlap false cases, this.crossesMidnight is true && other.crossesMidnight is false")
    fun t11() {
        val timeSlot1 = TimeSlot(startTimeInclusive = LocalTime.of(18, 0), endTimeInclusive = LocalTime.of(9, 0))
        val timeSlot2 = TimeSlot(startTimeInclusive = LocalTime.of(9, 0), endTimeInclusive = LocalTime.of(18, 0))
        assertThat(timeSlot1.isOverlap(timeSlot2)).isFalse()
        assertThat(timeSlot2.isOverlap(timeSlot1)).isFalse()
        val timeSlot3 = TimeSlot(startTimeInclusive = LocalTime.of(9, 1), endTimeInclusive = LocalTime.of(18, 0))
        assertThat(timeSlot1.isOverlap(timeSlot3)).isFalse()
        assertThat(timeSlot3.isOverlap(timeSlot1)).isFalse()
    }

    @Test
    @DisplayName("TimeSlot in TimeSlot")
    fun t12() {
        val timeSlot1 = TimeSlot(startTimeInclusive = LocalTime.of(23, 1), endTimeInclusive = LocalTime.of(0, 59))
        val timeSlot2 = TimeSlot(startTimeInclusive = LocalTime.of(23, 0), endTimeInclusive = LocalTime.of(1, 0))
        assertThat(timeSlot2 in timeSlot1).isFalse()
        assertThat(timeSlot1 in timeSlot2).isTrue()
    }
}
