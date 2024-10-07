package me.choicore.samples.pms.context

import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.Test
import java.time.LocalTime

class TimelineTests {
    @Test
    fun t1() {
        val slot1 = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0))
        val slot2 = TimeSlot(LocalTime.of(18, 0), LocalTime.of(9, 0))
        assertThatNoException()
            .isThrownBy {
                Timeline.create(slot1, slot2)
            }
    }

    @Test
    fun t2() {
        assertThatNoException()
            .isThrownBy {
                Timeline()
                    .add(LocalTime.of(9, 0), LocalTime.of(17, 0))
                    .add(TimeSlot(LocalTime.of(18, 0), LocalTime.of(9, 0)))
            }
    }

    @Test
    fun t3() {
        val slot2 = TimeSlot(LocalTime.of(18, 0), LocalTime.of(9, 0))
        assertThatNoException()
            .isThrownBy {
                Timeline()
                    .add(LocalTime.of(9, 0), LocalTime.of(17, 0))
                    .add(slot2)
            }
    }
}
