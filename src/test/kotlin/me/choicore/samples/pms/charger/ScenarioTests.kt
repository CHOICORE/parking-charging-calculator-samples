package me.choicore.samples.pms.charger

import me.choicore.samples.pms.context.TimeSlot
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatNoException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class ScenarioTests {
    @Nested
    inner class OneDayTests {
        @Test
        @DisplayName("beginAt is after timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is before endAt")
        fun t1() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 9, 30)
            val endAt = LocalDateTime.of(2024, 10, 1, 18, 1)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 30))
                assertThat(it.to)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            }

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is after timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is after endAt")
        fun t2() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 9, 30)
            val endAt = LocalDateTime.of(2024, 10, 1, 17, 59)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 30))
                assertThat(it.to)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 17, 59))
            }

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is after timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is same endAt")
        fun t3() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 9, 30)
            val endAt = LocalDateTime.of(2024, 10, 1, 18, 0)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 30))
                assertThat(it.to)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            }

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is same timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is before endAt")
        fun t4() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 9, 0)
            val endAt = LocalDateTime.of(2024, 10, 1, 19, 0)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(beginAt)
                assertThat(it.to)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            }

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is same timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is after endAt")
        fun t5() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 9, 0)
            val endAt = LocalDateTime.of(2024, 10, 1, 17, 59)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(beginAt)
                assertThat(it.to)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 17, 59))
            }

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is same timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is same endAt")
        fun t6() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 9, 0)
            val endAt = LocalDateTime.of(2024, 10, 1, 18, 0)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(beginAt)
                assertThat(it.to)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            }

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is before timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is same endAt")
        fun t7() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 9, 1)
            val endAt = LocalDateTime.of(2024, 10, 1, 18, 0)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 1))
                assertThat(it.to)
                    .isEqualTo(endAt)
            }

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is before timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is after endAt")
        fun t8() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 9, 1)
            val endAt = LocalDateTime.of(2024, 10, 1, 17, 59)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 1))
                assertThat(it.to)
                    .isEqualTo(endAt)
            }

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is before timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is before endAt")
        fun t9() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 9, 1)
            val endAt = LocalDateTime.of(2024, 10, 1, 18, 1)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            results.first().let {
                assertThat(it.from)
                    .isEqualTo(beginAt)
                assertThat(it.to)
                    .isEqualTo(endAt.toLocalDate().atTime(timeSlot.endTimeInclusive))
            }

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt, endAt not in all timeSlot && crossesMidnight = false")
        fun t10() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 0, 0)
            val endAt = LocalDateTime.of(2024, 10, 1, 1, 0)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            assertThat(results).isEmpty()

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt, endAt not in all timeSlot && crossesMidnight = true")
        fun t11() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 22, 0)
            val endAt = LocalDateTime.of(2024, 10, 1, 22, 59)

            val timeSlot = TimeSlot(LocalTime.of(23, 0), LocalTime.of(1, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            assertThat(results).isEmpty()

            displayResultsWithDuration(results)
        }
    }

    @Nested
    inner class ContinuousDaysTests {
        @Test
        @DisplayName("beginAt is after timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is before endAt")
        fun t1() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 10, 0)
            val endAt = LocalDateTime.of(2024, 10, 3, 18, 1)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 10, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 18, 0))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is after timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is after endAt")
        fun t2() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 10, 0)
            val endAt = LocalDateTime.of(2024, 10, 3, 17, 59)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 10, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 17, 59))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is after timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is same endAt")
        fun t3() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 10, 0)
            val endAt = LocalDateTime.of(2024, 10, 3, 18, 0)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 10, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 18, 0))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is same timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is before endAt")
        fun t4() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 9, 0)
            val endAt = LocalDateTime.of(2024, 10, 3, 18, 1)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 18, 0))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is same timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is after endAt")
        fun t5() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 9, 0)
            val endAt = LocalDateTime.of(2024, 10, 3, 17, 59)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 17, 59))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is same timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is same endAt")
        fun t6() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 9, 0)
            val endAt = LocalDateTime.of(2024, 10, 3, 18, 0)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 18, 0))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is before timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is before endAt")
        fun t7() {
            val from: LocalTime = LocalTime.of(9, 0)
            val to: LocalTime = LocalTime.of(18, 0)
            val timeSlot = TimeSlot(startTimeInclusive = from, endTimeInclusive = to)

            val beginAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 1), from.minusMinutes(1))
            val endAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 3), to.plusMinutes(1))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 18, 0))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is before timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is after endAt")
        fun t8() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 8, 59)
            val endAt = LocalDateTime.of(2024, 10, 3, 17, 59)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 17, 59))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is before timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is after endAt")
        fun t9() {
            val beginAt = LocalDateTime.of(2024, 10, 1, 8, 59)
            val endAt = LocalDateTime.of(2024, 10, 3, 18, 0)

            val timeSlot = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results

            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 9, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 18, 0))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is before timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is after endAt, crossesMidnight = true")
        fun t10() {
            val from: LocalTime = LocalTime.of(18, 0)
            val to: LocalTime = LocalTime.of(9, 0)
            val timeSlot = TimeSlot(startTimeInclusive = from, endTimeInclusive = to)

            val beginAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 1), LocalTime.of(17, 59))
            val endAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 3), LocalTime.of(9, 1))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results
            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is before timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is before endAt, crossesMidnight = true")
        fun t11() {
            val from: LocalTime = LocalTime.of(18, 0)
            val to: LocalTime = LocalTime.of(9, 0)
            val timeSlot = TimeSlot(startTimeInclusive = from, endTimeInclusive = to)

            val beginAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 1), LocalTime.of(17, 59))
            val endAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 3), LocalTime.of(8, 59))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results
            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 8, 59))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is before timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is same endAt, crossesMidnight = true")
        fun t12() {
            val from: LocalTime = LocalTime.of(18, 0)
            val to: LocalTime = LocalTime.of(9, 0)
            val timeSlot = TimeSlot(startTimeInclusive = from, endTimeInclusive = to)

            val beginAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 1), LocalTime.of(17, 59))
            val endAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 3), LocalTime.of(9, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results
            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is after timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is after endAt, crossesMidnight = true")
        fun t13() {
            val from: LocalTime = LocalTime.of(18, 0)
            val to: LocalTime = LocalTime.of(9, 0)
            val timeSlot = TimeSlot(startTimeInclusive = from, endTimeInclusive = to)

            val beginAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 1), LocalTime.of(18, 1))
            val endAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 3), LocalTime.of(9, 1))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results
            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 1))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is after timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is before endAt, crossesMidnight = true")
        fun t14() {
            val from: LocalTime = LocalTime.of(18, 0)
            val to: LocalTime = LocalTime.of(9, 0)
            val timeSlot = TimeSlot(startTimeInclusive = from, endTimeInclusive = to)

            val beginAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 1), LocalTime.of(18, 1))
            val endAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 3), LocalTime.of(8, 59))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results
            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 1))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 8, 59))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is after timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is same endAt, crossesMidnight = true")
        fun t15() {
            val from: LocalTime = LocalTime.of(18, 0)
            val to: LocalTime = LocalTime.of(9, 0)
            val timeSlot = TimeSlot(startTimeInclusive = from, endTimeInclusive = to)

            val beginAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 1), LocalTime.of(18, 1))
            val endAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 3), LocalTime.of(9, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results
            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 1))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is same timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is after endAt, crossesMidnight = true")
        fun t16() {
            val from: LocalTime = LocalTime.of(18, 0)
            val to: LocalTime = LocalTime.of(9, 0)
            val timeSlot = TimeSlot(startTimeInclusive = from, endTimeInclusive = to)

            val beginAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 1), LocalTime.of(18, 0))
            val endAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 3), LocalTime.of(9, 1))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results
            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is same timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is before endAt, crossesMidnight = true")
        fun t17() {
            val from: LocalTime = LocalTime.of(18, 0)
            val to: LocalTime = LocalTime.of(9, 0)
            val timeSlot = TimeSlot(startTimeInclusive = from, endTimeInclusive = to)

            val beginAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 1), LocalTime.of(18, 0))
            val endAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 3), LocalTime.of(8, 59))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results
            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 8, 59))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("beginAt is same timeSlot.startTimeInclusive && timeSlot.endTimeExclusive is same endAt, crossesMidnight = true")
        fun t18() {
            val from: LocalTime = LocalTime.of(18, 0)
            val to: LocalTime = LocalTime.of(9, 0)
            val timeSlot = TimeSlot(startTimeInclusive = from, endTimeInclusive = to)

            val beginAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 1), LocalTime.of(18, 0))
            val endAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 3), LocalTime.of(9, 0))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results
            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 18, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 9, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 18, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[3].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 9, 0))

            displayResultsWithDuration(results)
        }

        @Test
        @DisplayName("crossesMidnight = true")
        fun t19() {
            val from: LocalTime = LocalTime.of(23, 0)
            val to: LocalTime = LocalTime.of(0, 0)
            val timeSlot = TimeSlot(startTimeInclusive = from, endTimeInclusive = to)

            val beginAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 1), LocalTime.of(22, 0))
            val endAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 3), LocalTime.of(23, 59))

            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results
            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }
            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 23, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 23, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
            assertThat(results[2].from).isEqualTo(LocalDateTime.of(2024, 10, 3, 23, 0))
            assertThat(results[2].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 23, 59))

            val totalSumOfDurations = results.sumOf { it.toMinutes }
            println("총 적용 시간: ${totalSumOfDurations}분")
        }

        @Test
        @DisplayName("continuous days with crossesMidnight = true")
        fun t20() {
            val from: LocalTime = LocalTime.of(23, 0)
            val to: LocalTime = LocalTime.of(0, 0)
            val timeSlot = TimeSlot(startTimeInclusive = from, endTimeInclusive = to)

            val beginAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 1), LocalTime.of(0, 0))
            val endAt: LocalDateTime = LocalDateTime.of(LocalDate.of(2024, 10, 3), LocalTime.of(22, 59))
            val context = apply(beginAt, endAt, timeSlot)
            val results = context.results
            results.forEach {
                println("$it, 적용 시간: ${it.toMinutes}분")
            }
            assertThat(results[0].from).isEqualTo(LocalDateTime.of(2024, 10, 1, 23, 0))
            assertThat(results[0].to).isEqualTo(LocalDateTime.of(2024, 10, 2, 0, 0))
            assertThat(results[1].from).isEqualTo(LocalDateTime.of(2024, 10, 2, 23, 0))
            assertThat(results[1].to).isEqualTo(LocalDateTime.of(2024, 10, 3, 0, 0))
        }
    }

    private fun displayResultsWithDuration(results: MutableList<Result>) {
        results.forEach {
            println("$it, 적용 시간: ${it.toMinutes}분")
        }

        val totalSumOfDurations = results.sumOf { it.toMinutes }
        println("총 적용 시간: ${totalSumOfDurations}분")
    }

    fun apply(
        beginAt: LocalDateTime,
        endAt: LocalDateTime,
        timeSlot: TimeSlot,
    ): Context {
        val beginDate = beginAt.toLocalDate()
        val endDate = endAt.toLocalDate()

        val context = Context()

        if (beginDate == endDate) {
            evaluation(beginAt, endAt, timeSlot, context)
        } else {
            evaluation(beginAt, beginDate.atTime(LocalTime.MAX), timeSlot, context)

            var current = beginDate.plusDays(1)
            while (current.isBefore(endDate)) {
                evaluation(
                    current.atTime(LocalTime.MIN),
                    current.atTime(LocalTime.MAX),
                    timeSlot,
                    context,
                )
                current = current.plusDays(1)
            }

            evaluation(endDate.atStartOfDay(), endAt, timeSlot, context)
        }

        return context
    }

    private fun evaluation(
        from: LocalDateTime,
        to: LocalDateTime,
        timeSlot: TimeSlot,
        context: Context,
    ) {
        println("evaluate: $from ~ $to, $timeSlot")
        if (timeSlot.isFullTime) {
            context.add(Result(from, to, timeSlot))
            return
        }

        if (timeSlot.crossesMidnight) {
            inspect(from, to, timeSlot.crossesMidnightFirstTimeSlot, context)
            inspect(from, to, timeSlot.crossesMidnightSecondPart, context)
        } else {
            inspect(from, to, timeSlot, context)
        }
    }

    private fun inspect(
        from: LocalDateTime,
        to: LocalDateTime,
        timeSlot: TimeSlot?,
        context: Context,
    ) {
        if (timeSlot == null) {
            return
        }

        val start: LocalDateTime
        val end: LocalDateTime =
            if (to.toLocalTime() == LocalTime.MAX) {
                to.plusDays(1).truncatedTo(ChronoUnit.DAYS)
            } else {
                to
            }
        when {
            from.toLocalTime() in timeSlot && to.toLocalTime() in timeSlot -> {
                context.add(Result(from, end, timeSlot))
            }

            from.toLocalTime() in timeSlot && to.toLocalTime() !in timeSlot -> {
                start = from
                context.add(Result(start, to.toLocalDate().atTime(timeSlot.endTimeInclusive), timeSlot))
            }

            from.toLocalTime() !in timeSlot && to.toLocalTime() in timeSlot -> {
                start = from.toLocalDate().atTime(timeSlot.startTimeInclusive)
                context.add(Result(start, end, timeSlot))
            }

            from.toLocalTime() < timeSlot.startTimeInclusive && to.toLocalTime() > timeSlot.endTimeInclusive -> {
                start = from.toLocalDate().atTime(timeSlot.startTimeInclusive)
                context.add(Result(start, to.toLocalDate().atTime(timeSlot.endTimeInclusive), timeSlot))
            }
        }
    }

    @Test
    fun t1() {
        val slot1 = TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0))
        val slot2 = TimeSlot(LocalTime.of(18, 0), LocalTime.of(9, 0))

        assertThatNoException()
            .isThrownBy {
                Timeline()
                    .apply {
                        add(slot1)
                        add(slot2)
                    }
            }
    }

    @Test
    fun t2() {
        val slot1 = TimeSlot(LocalTime.of(9, 0), LocalTime.of(17, 0))
        val slot2 = TimeSlot(LocalTime.of(18, 0), LocalTime.of(9, 0))

        assertThatNoException()
            .isThrownBy {
                Timeline()
                    .apply {
                        add(slot1)
                        add(slot2)
                    }
            }
    }

    class Timeline {
        private val _slots: MutableList<TimeSlot> = mutableListOf()
        val slots: List<TimeSlot> get() = _slots.toList()

        fun add(slot: TimeSlot) {
            if (_slots.isEmpty()) {
                _slots.add(slot)
                return
            }

            _slots.forEach {
                require(!it.isOverlap(slot)) {
                    "TimeSlot is overlap"
                }
            }
            _slots.add(slot)
        }
    }

    class Context {
        val results: MutableList<Result> = mutableListOf()

        fun add(result: Result) {
            results.add(result)
        }
    }

    data class Result(
        val from: LocalDateTime,
        val to: LocalDateTime,
        val reason: TimeSlot,
    ) {
        val toMinutes get() = Duration.between(from, to).toMinutes()
    }

    data class Plan(
        val timeSlot: TimeSlot,
        val policyType: PolicyType,
        val rate: Double,
    )

    enum class PolicyType {
        SURCHARGE,
        DISCOUNT,
    }

    class DayOfWeekRule {
        val dayOfWeek: DayOfWeek
        val slots: List<TimeSlot>
        private val selectedDate: LocalDate?

        constructor(selectedDate: LocalDate, timeSlots: List<TimeSlot>) {
            this.dayOfWeek = selectedDate.dayOfWeek
            this.slots = timeSlots
            this.selectedDate = selectedDate
        }

        constructor(dayOfWeek: DayOfWeek, timeSlots: List<TimeSlot>) {
            this.dayOfWeek = dayOfWeek
            this.slots = timeSlots
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
