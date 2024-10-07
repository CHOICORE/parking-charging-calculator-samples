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
        } else {
            null
        }

    val crossesMidnightSecondPart: TimeSlot? =
        if (this.crossesMidnight) TimeSlot(this.startTimeInclusive, LocalTime.MAX) else null

    val isFullTime: Boolean = (this.startTimeInclusive == LocalTime.MIN) && (this.endTimeInclusive == LocalTime.MAX)

    val totalMinutes: Long =
        when {
            this.isFullTime -> FULL_DAY_OF_MINUTES
            this.crossesMidnight -> {
                val firstPart: Long = Duration.between(this.startTimeInclusive, LocalTime.MAX).toMinutes() + 1
                val secondPart: Long = Duration.between(LocalTime.MIDNIGHT, this.endTimeInclusive).toMinutes()
                firstPart + secondPart
            }

            else -> Duration.between(this.startTimeInclusive, this.endTimeInclusive).toMinutes()
        }

    fun isOverlap(other: TimeSlot): Boolean =
        when {
            // 둘 다 자정을 넘기는 경우 무조건 겹침
            this.crossesMidnight && other.crossesMidnight -> true

            !this.crossesMidnight && other.crossesMidnight ->
                other.startTimeInclusive.isBefore(this.endTimeInclusive) ||
                    other.endTimeInclusive.isAfter(this.startTimeInclusive)

            this.crossesMidnight ->
                this.startTimeInclusive.isBefore(other.endTimeInclusive) ||
                    this.endTimeInclusive.isAfter(other.startTimeInclusive)

            else ->
                this.endTimeInclusive.isAfter(other.startTimeInclusive) &&
                    this.startTimeInclusive.isBefore(other.endTimeInclusive)
        }

    operator fun contains(other: TimeSlot): Boolean = other.startTimeInclusive in this && other.endTimeInclusive in this

    operator fun contains(time: LocalTime): Boolean =
        if (!this.crossesMidnight) {
            time in this.startTimeInclusive..this.endTimeInclusive
        } else {
            (this.crossesMidnightSecondPart != null && time in this.crossesMidnightSecondPart) ||
                (this.crossesMidnightFirstTimeSlot != null && time in this.crossesMidnightFirstTimeSlot)
        }

    companion object {
        const val FULL_DAY_OF_MINUTES = 24 * 60L
        val FULL_TIME = TimeSlot(LocalTime.MIN, LocalTime.MAX)
    }
}
