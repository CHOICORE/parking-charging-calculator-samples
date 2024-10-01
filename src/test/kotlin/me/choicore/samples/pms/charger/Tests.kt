package me.choicore.samples.pms.charger

import me.choicore.samples.pms.context.Timebase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class Tests {
    @Test
    @DisplayName("시간 범위가 하루를 넘어가는 경우")
    fun t2() {
        // 2024-10-01 09:00
        val arrivedAt = LocalDateTime.of(2024, 10, 1, 16, 30)
        // 2024-11-01 00:00
        val departedAt = LocalDateTime.of(2024, 11, 1, 0, 0)
        val results = apply(arrivedAt, departedAt, Timebase.FULL_TIME)
        results.forEach {
            println("$it, 차감 시간: ${it.toMinutes} 분")
        }

        val totalSumOfDurations = results.sumOf { it.toMinutes }
        println("총 차감 시간: $totalSumOfDurations")
    }

    @Test
    fun t3() {
        // 2024-10-01 09:00
        val arrivedAt = LocalDateTime.of(2024, 10, 1, 16, 30)
        // 2024-11-01 00:00
        val departedAt = LocalDateTime.of(2024, 10, 1, 21, 0)

        // 09:00 - 18:00 (9시간)
        val timebase = Timebase(LocalTime.of(9, 0), LocalTime.of(18, 0))

        val results = apply(arrivedAt, departedAt, timebase)
        results.forEach {
            println("$it, 차감 시간: ${it.toMinutes}분")
        }

        val totalSumOfDurations = results.sumOf { it.toMinutes }
        println("총 차감 시간: ${totalSumOfDurations}분")
    }



    fun apply(
        arrivedAt: LocalDateTime,
        departedAt: LocalDateTime,
        timebase: Timebase,
    ): List<Result> {
        val beginDate = arrivedAt.toLocalDate()
        val endDate = departedAt.toLocalDate()

        val applies = mutableListOf<Result>()
        if (beginDate == endDate) {
            // 도착일과 출발일이 같은 경우
            analyze(arrivedAt, departedAt, timebase)?.let { applies.add(it) }
        } else {
            // 첫날 처리 (도착 시점부터 그날 자정까지)
            analyze(arrivedAt, beginDate.plusDays(1).atStartOfDay(), timebase)?.let { applies.add(it) }

            // 중간 날짜 처리 (while 루프 사용)
            var current = beginDate.plusDays(1)
            while (current.isBefore(endDate)) {
                analyze(
                    current.atStartOfDay(),
                    current.plusDays(1).atStartOfDay(),
                    timebase
                )?.let { applies.add(it) }
                current = current.plusDays(1)
            }

            // 마지막 날 처리 (자정부터 출발 시점까지)
            analyze(endDate.atStartOfDay(), departedAt, timebase)?.let { applies.add(it) }
        }
        return applies.toList()
    }

    data class Result(
        val from: LocalDateTime,
        val to: LocalDateTime,
        val reason: Timebase,
    ) {
        val toMinutes get() = Duration.between(from, to).toMinutes()
    }

    fun analyze(
        from: LocalDateTime,
        to: LocalDateTime,
        timebase: Timebase,
    ): Result? {
        println("$from ~ $to, $timebase")
        if (from == to) {
            return null
        }


        val effected1 = timebase.contains(from.toLocalTime())
        val effected2 = timebase.contains(to.toLocalTime())

        println(effected1)
        println(effected2)

        return null
    }

//    fun process(from: LocalDateTime, to: LocalDateTime, timebase: Timebase): Result? {
//        if (from == to) {
//            return null
//        }
//        val adjustedMin = from.toLocalDate().atTime(timebase.from)
//        var adjustedMax = to.toLocalDate().atTime(timebase.to)
//        if (timebase.crossesMidnight) {
//            if (adjustedMin in from..to && to <= adjustedMax) {
//                if (to < adjustedMax) {
//                    adjustedMax = to
//                }
//                return Result(adjustedMin, adjustedMax, timebase)
//            }
//
//            if (from <= adjustedMax) {
//                if (to < adjustedMax) {
//                    adjustedMax = to
//                }
//                return Result(from, adjustedMax, timebase)
//            }
//
//            return null
//        } else {
//            if (adjustedMin in from..to) {
//                if (to < adjustedMax) {
//                    adjustedMax = to
//                }
//
//                return Result(adjustedMin, adjustedMax, timebase)
//
//            }
//
//            if (from <= adjustedMax) {
//                if (to < adjustedMax) {
//                    adjustedMax = to
//                }
//                return Result(from, adjustedMax, timebase)
//            }
//
//            return null
//        }
//
//    }

    data class Plan(
        val timebase: Timebase,
        val policyType: PolicyType,
        val rate: Double,
    )

    enum class PolicyType {
        SURCHARGE,
        DISCOUNT
    }


    class DayOfWeekRule {
        val dayOfWeek: DayOfWeek
        val slots: List<Timebase>
        private val selectedDate: LocalDate?

        constructor(selectedDate: LocalDate, timebases: List<Timebase>) {
            this.dayOfWeek = selectedDate.dayOfWeek
            this.slots = timebases
            this.selectedDate = selectedDate
        }

        constructor(dayOfWeek: DayOfWeek, timebases: List<Timebase>) {
            this.dayOfWeek = dayOfWeek
            this.slots = timebases
            this.selectedDate = null
        }

        fun isApplicable(date: LocalDate): Boolean {
            return if (selectedDate != null) {
                date == selectedDate
            } else {
                date.dayOfWeek == dayOfWeek
            }
        }
    }

}