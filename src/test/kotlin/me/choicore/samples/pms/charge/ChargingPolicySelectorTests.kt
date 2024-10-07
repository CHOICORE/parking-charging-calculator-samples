package me.choicore.samples.pms.charge

import me.choicore.samples.pms.charge.ChargingMethod.DISCOUNT
import me.choicore.samples.pms.context.TimeSlot
import me.choicore.samples.pms.context.Timeline
import me.choicore.samples.pms.context.Timeline.Companion
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.DayOfWeek.WEDNESDAY
import java.time.LocalDate
import java.time.LocalTime

class ChargingPolicySelectorTests {
    @Test
    fun t1() {
        val specifiedDatePolicy =
            SpecifiedDateChargingPolicy(
                name = "한글날 무료 개방",
                options = listOf(ChargingOption(method = DISCOUNT, rateUnit = 0.5, timeline = Companion.FULL_TIME)),
                specifiedDate = LocalDate.of(2024, 10, 9),
            )

        val timeline: Timeline = Companion.FULL_TIME
        val chargingOption = ChargingOption(method = DISCOUNT, rateUnit = 0.5, timeline = timeline)
        val dayOfWeekChargingPolicy = DayOfWeekChargingPolicy("평일 무료 개방", listOf(chargingOption), WEDNESDAY)
        val chargingPolicySelector = ChargingPolicySelector(listOf(specifiedDatePolicy, dayOfWeekChargingPolicy))

        val selected: ChargingPolicy = chargingPolicySelector.select(LocalDate.of(2024, 10, 9))
        assertThat(selected).isEqualTo(specifiedDatePolicy)
    }

    @Test
    fun t2() {
        val timeline: Timeline =
            Companion.create(
                TimeSlot(LocalTime.of(9, 0), LocalTime.of(18, 0)),
                TimeSlot(LocalTime.of(22, 0), LocalTime.of(4, 0)),
            )
        val specifiedDatePolicy =
            SpecifiedDateChargingPolicy(
                name = "한글날 무료 개방",
                options = listOf(ChargingOption(method = DISCOUNT, rateUnit = 0.5, timeline = Companion.FULL_TIME)),
                specifiedDate = LocalDate.of(2024, 10, 9),
            )

        val chargingOption = ChargingOption(method = DISCOUNT, rateUnit = 0.5, timeline = timeline)
        val dayOfWeekChargingPolicy = DayOfWeekChargingPolicy("평일 무료 개방", listOf(chargingOption), WEDNESDAY)
        val chargingPolicySelector = ChargingPolicySelector(listOf(specifiedDatePolicy, dayOfWeekChargingPolicy))
        val date: LocalDate = LocalDate.of(2024, 10, 16)
        val selected: ChargingPolicy = chargingPolicySelector.select(date)

        assertThat(selected).isEqualTo(dayOfWeekChargingPolicy)
    }
}
