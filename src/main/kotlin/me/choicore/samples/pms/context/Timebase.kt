package me.choicore.samples.pms.context

import java.time.Duration
import java.time.LocalTime

data class Timebase(
    val from: LocalTime,
    val to: LocalTime,
) {
    val crossesMidnight: Boolean
        get() = from >= to

    val isFullTime: Boolean
        get() = from == LocalTime.MIN && to == LocalTime.MAX || from == to

    fun duration(): Long {
        return if (isFullTime) {
            FULL_DAY_OF_MINUTES
        } else if (crossesMidnight) {
            (Duration.between(from, LocalTime.MAX) + Duration.between(LocalTime.MIN, to)).toMinutes() + 1
        } else {
            Duration.between(from, to).toMinutes()
        }
    }

    operator fun contains(time: LocalTime): Boolean {
        return if (crossesMidnight) {
            time >= from || time <= to
        } else {
            time in from..to
        }
    }

    companion object {
        const val FULL_DAY_OF_MINUTES = 24 * 60L
        val FULL_TIME = Timebase(LocalTime.MIN, LocalTime.MAX)
    }
}