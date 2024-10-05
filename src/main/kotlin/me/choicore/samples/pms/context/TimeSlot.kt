package me.choicore.samples.pms.context

import java.time.Duration
import java.time.LocalTime

data class TimeSlot(
    val startTimeInclusive: LocalTime,
    val endTimeInclusive: LocalTime,
) {
    init {
        require(this.startTimeInclusive != this.endTimeInclusive) { "시작 시간과 종료 시간이 같을 수 없습니다." }
    }

    val crossesMidnight: Boolean = this.startTimeInclusive > this.endTimeInclusive

    val crossesMidnightFirstTimeSlot: TimeSlot? =
        if (this.crossesMidnight && this.endTimeInclusive != LocalTime.MIDNIGHT
        ) {
            TimeSlot(LocalTime.MIDNIGHT, this.endTimeInclusive)
        } else null

    val crossesMidnightSecondPart: TimeSlot? =
        if (this.crossesMidnight) TimeSlot(this.startTimeInclusive, LocalTime.MAX) else null

    val isFullTime: Boolean = (this.startTimeInclusive == LocalTime.MIN) && (this.endTimeInclusive == LocalTime.MAX)

    val totalMinutes: Long = when {
        this.isFullTime -> FULL_DAY_OF_MINUTES
        this.crossesMidnight -> {
            val firstPart: Long = Duration.between(this.startTimeInclusive, LocalTime.MAX).toMinutes() + 1
            val secondPart: Long = Duration.between(LocalTime.MIDNIGHT, this.endTimeInclusive).toMinutes()
            firstPart + secondPart
        }

        else -> Duration.between(this.startTimeInclusive, this.endTimeInclusive).toMinutes()
    }

    fun isOverlap(other: TimeSlot): Boolean {
        TODO("Not yet implemented")
    }

    operator fun contains(time: LocalTime): Boolean =
        if (this.crossesMidnight) {
            val firstPart: Boolean = this.crossesMidnightSecondPart?.let { time in it } ?: false
            val secondPart: Boolean = this.crossesMidnightFirstTimeSlot?.let { time in it } ?: false
            firstPart && secondPart
        } else {
            time in this.startTimeInclusive..this.endTimeInclusive
        }

    companion object {
        const val FULL_DAY_OF_MINUTES = 24 * 60L
        val FULL_TIME = TimeSlot(LocalTime.MIN, LocalTime.MAX)
    }
}
