package me.choicore.samples.pms.context

import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class FunctionalityTests {
    @Test
    fun t1() {
        val beginAt: LocalDateTime = LocalDateTime.of(2024, 10, 6, 12, 0, 0)
        val endAt: LocalDateTime = LocalDateTime.of(2024, 10, 9, 0, 0, 0)
        divideByDays(beginAt, endAt)
    }

    fun divideByDays(
        startDateInclusive: LocalDateTime,
        endDateInclusive: LocalDateTime,
    ) {
        // 첫째, 시작 날짜와 종료 날짜가 같은 경우 또는 종료 시간이 시작 날짜의 다음날 시작 시간인 경우 바로 반환
        if (startDateInclusive.isEqualDate(endDateInclusive) || endDateInclusive == startDateInclusive.atStartOfNextDay()) {
            println("$startDateInclusive ~ $endDateInclusive")
            return
        }

        // 첫날 처리
        var current: LocalDate = startDateInclusive.toLocalDate()
        val end: LocalDate = endDateInclusive.toLocalDate()
        println("$startDateInclusive ~ ${current.atStartOfNextDay()}")

        // 중간날 처리
        current = current.nextDay()
        while (current.isBefore(end)) {
            println("${current.atStartOfDay()} ~ ${current.atStartOfNextDay()}")
            current = current.nextDay()
        }

        // 마지막날 처리
        if (current.atStartOfDay() == endDateInclusive) return
        println("${end.atStartOfDay()} ~ $endDateInclusive")
    }

    fun LocalDateTime.isEqualDate(other: LocalDateTime): Boolean = this.toLocalDate() == other.toLocalDate()

    fun LocalDateTime.atStartOfDay(): LocalDateTime = this.toLocalDate().atStartOfDay()

    fun LocalDateTime.atStartOfNextDay(): LocalDateTime = this.toLocalDate().plusDays(1).atStartOfDay()

    fun LocalDate.atStartOfNextDay(): LocalDateTime = this.plusDays(1).atStartOfDay()

    fun LocalDate.nextDay(): LocalDate = this.plusDays(1)
}
