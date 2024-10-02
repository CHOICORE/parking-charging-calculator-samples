package me.choicore.samples.pms.charger

import me.choicore.samples.pms.context.TimeBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class Tests {
    @Nested
    inner class OneDayTests {
        @Test
        @DisplayName("arrivedAt > timeBase.from && timeBase.to < departedAt")
        fun t1() {
            val arrivedAt = LocalDateTime.of(2024, 10, 1, 9, 30)
            val departedAt = LocalDateTime.of(2024, 10, 1, 18, 1)

            val timeBase = TimeBase(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val results = apply(arrivedAt, departedAt, timeBase)

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 30))
                assertThat(it.to)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            }

            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }

            val totalSumOfDurations = results.sumOf { it.toMinutes }
            println("총 적용 시간: ${totalSumOfDurations}분")
        }

        @Test
        @DisplayName("arrivedAt > timeBase.from && timeBase.to > departedAt")
        fun t2() {
            val arrivedAt = LocalDateTime.of(2024, 10, 1, 9, 30)
            val departedAt = LocalDateTime.of(2024, 10, 1, 17, 59)

            val timeBase = TimeBase(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val results = apply(arrivedAt, departedAt, timeBase)

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 30))
                assertThat(it.to)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 17, 59))
            }

            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }

            val totalSumOfDurations = results.sumOf { it.toMinutes }
            println("총 적용 시간: ${totalSumOfDurations}분")
        }

        @Test
        @DisplayName("arrivedAt > timeBase.from && timeBase.to == departedAt")
        fun t3() {
            val arrivedAt = LocalDateTime.of(2024, 10, 1, 9, 30)
            val departedAt = LocalDateTime.of(2024, 10, 1, 18, 0)

            val timeBase = TimeBase(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val results = apply(arrivedAt, departedAt, timeBase)

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 30))
                assertThat(it.to)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            }

            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }

            val totalSumOfDurations = results.sumOf { it.toMinutes }
            println("총 적용 시간: ${totalSumOfDurations}분")
        }

        @Test
        @DisplayName("arrivedAt == timeBase.from && timeBase.to < departedAt")
        fun t4() {
            val arrivedAt = LocalDateTime.of(2024, 10, 1, 9, 0)
            val departedAt = LocalDateTime.of(2024, 10, 1, 19, 0)

            val timeBase = TimeBase(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val results = apply(arrivedAt, departedAt, timeBase)

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(arrivedAt)
                assertThat(it.to)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            }

            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }

            val totalSumOfDurations = results.sumOf { it.toMinutes }
            println("총 적용 시간: ${totalSumOfDurations}분")
        }

        @Test
        @DisplayName("arrivedAt == timeBase.from && timeBase.to > departedAt")
        fun t5() {
            val arrivedAt = LocalDateTime.of(2024, 10, 1, 9, 0)
            val departedAt = LocalDateTime.of(2024, 10, 1, 17, 59)

            val timeBase = TimeBase(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val results = apply(arrivedAt, departedAt, timeBase)

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(arrivedAt)
                assertThat(it.to)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 17, 59))
            }

            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }

            val totalSumOfDurations = results.sumOf { it.toMinutes }
            println("총 적용 시간: ${totalSumOfDurations}분")
        }

        @Test
        @DisplayName("arrivedAt == timeBase.from && timeBase.to == departedAt")
        fun t6() {
            val arrivedAt = LocalDateTime.of(2024, 10, 1, 9, 0)
            val departedAt = LocalDateTime.of(2024, 10, 1, 18, 0)

            val timeBase = TimeBase(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val results = apply(arrivedAt, departedAt, timeBase)

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(arrivedAt)
                assertThat(it.to)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            }

            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }

            val totalSumOfDurations = results.sumOf { it.toMinutes }
            println("총 적용 시간: ${totalSumOfDurations}분")
        }

        @Test
        @DisplayName("arrivedAt < timeBase.from && timeBase.to == departedAt")
        fun t7() {
            val arrivedAt = LocalDateTime.of(2024, 10, 1, 9, 1)
            val departedAt = LocalDateTime.of(2024, 10, 1, 18, 0)

            val timeBase = TimeBase(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val results = apply(arrivedAt, departedAt, timeBase)

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 1))
                assertThat(it.to)
                    .isEqualTo(departedAt)
            }

            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }

            val totalSumOfDurations = results.sumOf { it.toMinutes }
            println("총 적용 시간: ${totalSumOfDurations}분")
        }

        @Test
        @DisplayName("arrivedAt < timeBase.from && timeBase.to > departedAt")
        fun t8() {
            val arrivedAt = LocalDateTime.of(2024, 10, 1, 9, 1)
            val departedAt = LocalDateTime.of(2024, 10, 1, 17, 59)

            val timeBase = TimeBase(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val results = apply(arrivedAt, departedAt, timeBase)

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 1))
                assertThat(it.to)
                    .isEqualTo(departedAt)
            }

            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }

            val totalSumOfDurations = results.sumOf { it.toMinutes }
            println("총 적용 시간: ${totalSumOfDurations}분")
        }

        @Test
        @DisplayName("arrivedAt < timeBase.from && timeBase.to < departedAt")
        fun t9() {
            val arrivedAt = LocalDateTime.of(2024, 10, 1, 9, 1)
            val departedAt = LocalDateTime.of(2024, 10, 1, 18, 1)

            val timeBase = TimeBase(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val results = apply(arrivedAt, departedAt, timeBase)

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(arrivedAt)
                assertThat(it.to)
                    .isEqualTo(departedAt.toLocalDate().atTime(timeBase.to))
            }

            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }

            val totalSumOfDurations = results.sumOf { it.toMinutes }
            println("총 적용 시간: ${totalSumOfDurations}분")
        }

        @Test
        @DisplayName("arrivedAt, departedAt not in all timeBase && crossesMidnight = false")
        fun t10() {
            val arrivedAt = LocalDateTime.of(2024, 10, 1, 0, 0)
            val departedAt = LocalDateTime.of(2024, 10, 1, 1, 0)

            val timeBase = TimeBase(LocalTime.of(9, 0), LocalTime.of(18, 0))

            val results = apply(arrivedAt, departedAt, timeBase)

            assertThat(results).isEmpty()

            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }

            val totalSumOfDurations = results.sumOf { it.toMinutes }
            println("총 적용 시간: ${totalSumOfDurations}분")
        }

        @Test
        @DisplayName("arrivedAt, departedAt not in all timeBase && crossesMidnight = true")
        fun t11() {
            val arrivedAt = LocalDateTime.of(2024, 10, 1, 22, 0)
            val departedAt = LocalDateTime.of(2024, 10, 1, 22, 59)

            val timeBase = TimeBase(LocalTime.of(23, 0), LocalTime.of(1, 0))

            val results = apply(arrivedAt, departedAt, timeBase)

            assertThat(results).isEmpty()

            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }

            val totalSumOfDurations = results.sumOf { it.toMinutes }
            println("총 적용 시간: ${totalSumOfDurations}분")
        }
    }

    @Nested
    inner class MultiDayTests

    fun apply(
        arrivedAt: LocalDateTime,
        departedAt: LocalDateTime,
        timeBase: TimeBase,
    ): List<Result> {
        val beginDate = arrivedAt.toLocalDate()
        val endDate = departedAt.toLocalDate()

        val applies = mutableListOf<Result>()

        if (beginDate == endDate) {
            evaluation(arrivedAt, departedAt, timeBase)?.let { applies.add(it) }
        } else {
            evaluation(arrivedAt, beginDate.atTime(LocalTime.MAX), timeBase)?.let { applies.add(it) }

            var current = beginDate.plusDays(1)
            while (current.isBefore(endDate)) {
                evaluation(
                    current.atTime(LocalTime.MIN),
                    current.atTime(LocalTime.MAX),
                    timeBase,
                )?.let { applies.add(it) }
                current = current.plusDays(1)
            }

            evaluation(endDate.atStartOfDay(), departedAt, timeBase)?.let { applies.add(it) }
        }

        return applies.toList()
    }

    private fun evaluation(
        from: LocalDateTime,
        to: LocalDateTime,
        timeBase: TimeBase,
    ): Result? {
        println("$from ~ $to, $timeBase")

        if (!timeBase.contains(from.toLocalTime()) && !timeBase.contains(to.toLocalTime())) {
            return null
        }

        var start: LocalDateTime = from
        var end: LocalDateTime = to

        if (timeBase.crossesMidnight) {
            if (from.toLocalTime() <= timeBase.from) {
                start = from.toLocalDate().atTime(timeBase.from)
                if (start > to) {
                    start = from.toLocalDate().atStartOfDay()
                }
            }

            if (to.toLocalTime() == LocalTime.MAX) {
                end = to.toLocalDate().plusDays(1).atStartOfDay()
            } else {
                if (to.toLocalTime() > timeBase.to) {
                    end = to.toLocalDate().atTime(timeBase.to)
                }
            }

            return Result(start, end, timeBase)
        } else {
            if (from.toLocalTime() < timeBase.from) {
                start = from.toLocalDate().atTime(timeBase.from)
            }

            if (to.toLocalTime() == LocalTime.MAX) {
                if (timeBase.to == LocalTime.MAX) {
                    end = to.toLocalDate().plusDays(1).atStartOfDay()
                }
                if (to.toLocalTime() > timeBase.to) {
                    end = to.toLocalDate().atTime(timeBase.to)
                }
            } else {
                if (to.toLocalTime() > timeBase.to) {
                    end = to.toLocalDate().atTime(timeBase.to)
                }
            }

            return Result(start, end, timeBase)
        }
    }

    data class Result(
        val from: LocalDateTime,
        val to: LocalDateTime,
        val reason: TimeBase,
    ) {
        val toMinutes get() = Duration.between(from, to).toMinutes()
    }

    data class Plan(
        val timeBase: TimeBase,
        val policyType: PolicyType,
        val rate: Double,
    )

    enum class PolicyType {
        SURCHARGE,
        DISCOUNT,
    }

    class DayOfWeekRule {
        val dayOfWeek: DayOfWeek
        val slots: List<TimeBase>
        private val selectedDate: LocalDate?

        constructor(selectedDate: LocalDate, timeBases: List<TimeBase>) {
            this.dayOfWeek = selectedDate.dayOfWeek
            this.slots = timeBases
            this.selectedDate = selectedDate
        }

        constructor(dayOfWeek: DayOfWeek, timeBases: List<TimeBase>) {
            this.dayOfWeek = dayOfWeek
            this.slots = timeBases
            this.selectedDate = null
        }

        fun isApplicable(date: LocalDate): Boolean =
            if (selectedDate != null) {
                date == selectedDate
            } else {
                date.dayOfWeek == dayOfWeek
            }
    }
}
