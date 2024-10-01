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
        val arrivedAt = LocalDateTime.of(2024, 10, 1, 16, 0)
        val departedAt = LocalDateTime.of(2024, 10, 3, 12, 0)
        val results = apply(arrivedAt, departedAt, Timebase.FULL_TIME)
        results.forEach {
            println("$it, 적용 시간: ${it.toMinutes} 분")
        }

        val totalSumOfDurations = results.sumOf { it.toMinutes }
        println("총 적용 시간: $totalSumOfDurations")
    }

    @Test
    fun t3() {
        val arrivedAt = LocalDateTime.of(2024, 10, 1, 16, 30)
        val departedAt = LocalDateTime.of(2024, 10, 1, 21, 0)

        val timebase = Timebase(LocalTime.of(9, 0), LocalTime.of(18, 0))

        val results = apply(arrivedAt, departedAt, timebase)
        results.forEach {
            println("$it, 적용 시간: ${it.toMinutes}분")
        }

        val totalSumOfDurations = results.sumOf { it.toMinutes }
        println("총 적용 시간: ${totalSumOfDurations}분")
    }

    @Test
    fun t4() {
        val arrivedAt = LocalDateTime.of(2024, 10, 1, 18, 0)
        val departedAt = LocalDateTime.of(2024, 10, 2, 0, 30)

        val timebase = Timebase(LocalTime.of(23, 0), LocalTime.of(1, 0))

        val results = apply(arrivedAt, departedAt, timebase)
        results.forEach {
            println("$it, 적용 시간: ${it.toMinutes}분")
        }

        val totalSumOfDurations = results.sumOf { it.toMinutes }
        println("총 적용 시간: ${totalSumOfDurations}분")
    }

    @Test
    fun t5() {
        val arrivedAt = LocalDateTime.of(2024, 10, 1, 23, 0)
        val departedAt = LocalDateTime.of(2024, 10, 11, 2, 0)

        val timebase = Timebase(LocalTime.of(23, 0), LocalTime.of(1, 30))

        val results = apply(arrivedAt, departedAt, timebase)
        results.forEach {
            println("$it, 적용 시간: ${it.toMinutes}분")
        }

        val totalSumOfDurations = results.sumOf { it.toMinutes }
        println("총 적용 시간: ${totalSumOfDurations}분")
    }

    @Test
    fun t6() {
        val arrivedAt = LocalDateTime.of(2024, 10, 1, 23, 30)
        val departedAt = LocalDateTime.of(2024, 10, 3, 2, 30)

        val timebase = Timebase(LocalTime.of(23, 0), LocalTime.of(1, 30))

        val results = apply(arrivedAt, departedAt, timebase)
        results.forEach {
            println("$it, 적용 시간: ${it.toMinutes}분")
        }

        val totalSumOfDurations = results.sumOf { it.toMinutes }
        println("총 적용 시간: ${totalSumOfDurations}분")
    }

    @Test
    fun t7() {
        val arrivedAt = LocalDateTime.of(2024, 10, 1, 9, 0)
        val departedAt = LocalDateTime.of(2024, 10, 3, 21, 0)

        val timebase = Timebase(LocalTime.of(18, 0), LocalTime.of(22, 30))

        val results = apply(arrivedAt, departedAt, timebase)
        results.forEach {
            println("$it, 적용 시간: ${it.toMinutes}분")
        }

        val totalSumOfDurations = results.sumOf { it.toMinutes }
        println("총 적용 시간: ${totalSumOfDurations}분")
    }

    @Test
    fun t8() {
        val arrivedAt = LocalDateTime.of(2024, 10, 1, 9, 0)
        val departedAt = LocalDateTime.of(2024, 10, 3, 21, 0)

        val timebase = Timebase(LocalTime.of(18, 0), LocalTime.of(22, 30))

        val results = apply(arrivedAt, departedAt, timebase)
        results.forEach {
            println("$it, 적용 시간: ${it.toMinutes}분")
        }

        val totalSumOfDurations = results.sumOf { it.toMinutes }
        println("총 적용 시간: ${totalSumOfDurations}분")
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
            analyze(arrivedAt, departedAt, timebase)?.let { applies.add(it) }
        } else {
            analyze(arrivedAt, beginDate.atTime(LocalTime.MAX), timebase)?.let { applies.add(it) }

            var current = beginDate.plusDays(1)
            while (current.isBefore(endDate)) {
                analyze(
                    current.atTime(LocalTime.MIN),
                    current.atTime(LocalTime.MAX),
                    timebase
                )?.let { applies.add(it) }
                current = current.plusDays(1)
            }

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

        var start: LocalDateTime = from
        var end: LocalDateTime = to

        if (timebase.crossesMidnight) {
            if (from.toLocalTime() <= timebase.from) {
                start = from.toLocalDate().atTime(timebase.from)
                if (start > to) {
                    start = from.toLocalDate().atStartOfDay()
                }
            }

            if (to.toLocalTime() == LocalTime.MAX) {
                end = to.toLocalDate().plusDays(1).atStartOfDay()
            } else {
                if (to.toLocalTime() > timebase.to) {
                    end = to.toLocalDate().atTime(timebase.to)
                }
            }

            return Result(start, end, timebase)
        } else {
            if (from.toLocalTime() < timebase.from) {
                start = from.toLocalDate().atTime(timebase.from)
            }


            if (to.toLocalTime() == LocalTime.MAX) {
                if (timebase.to == LocalTime.MAX) {
                    end = to.toLocalDate().plusDays(1).atStartOfDay()
                }
                if (to.toLocalTime() > timebase.to) {
                    end = to.toLocalDate().atTime(timebase.to)
                }
            } else {
                if (to.toLocalTime() > timebase.to) {
                    end = to.toLocalDate().atTime(timebase.to)
                }
            }

            return Result(start, end, timebase)
        }
    }

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