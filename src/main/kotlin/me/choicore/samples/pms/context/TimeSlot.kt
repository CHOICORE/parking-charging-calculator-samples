package me.choicore.samples.pms.context

import java.time.Duration
import java.time.LocalTime

data class TimeSlot(
    val startTimeInclusive: LocalTime,
    val endTimeExclusive: LocalTime,
) {
    init {
        require(this.startTimeInclusive != this.endTimeExclusive) { "시작 시간과 끝 시간이 같을 수 없습니다." }
    }

    val crossesMidnight: Boolean = this.startTimeInclusive > this.endTimeExclusive

    val isFullTime: Boolean = (this.startTimeInclusive == LocalTime.MIN) && (this.endTimeExclusive == LocalTime.MAX)

    val totalMinutes: Long = when {
        isFullTime -> FULL_DAY_OF_MINUTES
        this.crossesMidnight -> {
            val firstPart: Long = Duration.between(this.startTimeInclusive, LocalTime.MAX).toMinutes() + 1
            val secondPart: Long = Duration.between(LocalTime.MIDNIGHT, this.endTimeExclusive).toMinutes()
            firstPart + secondPart
        }

        else -> Duration.between(this.startTimeInclusive, this.endTimeExclusive).toMinutes()
    }

    operator fun contains(time: LocalTime): Boolean =
        if (this.crossesMidnight) {
            (time >= this.startTimeInclusive) || (time <= this.endTimeExclusive)
        } else {
            time in (this.startTimeInclusive..this.endTimeExclusive)
        }

    companion object {
        const val FULL_DAY_OF_MINUTES = 24 * 60L
        val FULL_TIME = TimeSlot(LocalTime.MIN, LocalTime.MAX)
    }
}
