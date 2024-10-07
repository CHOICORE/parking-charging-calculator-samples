package me.choicore.samples.pms.charge

import java.time.DayOfWeek
import java.time.LocalDate

class DayOfWeekChargingPolicy(
    override val name: String,
    val options: List<ChargingOption>,
    val dayOfWeek: DayOfWeek,
) : ChargingPolicy {
    override fun satisfiedBy(date: LocalDate): Boolean = this.dayOfWeek == date.dayOfWeek
}
