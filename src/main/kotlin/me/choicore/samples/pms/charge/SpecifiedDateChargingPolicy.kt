package me.choicore.samples.pms.charge

import java.time.LocalDate

class SpecifiedDateChargingPolicy(
    override val name: String,
    val options: List<ChargingOption>,
    val specifiedDate: LocalDate,
) : ChargingPolicy {
    override fun satisfiedBy(date: LocalDate): Boolean = date == specifiedDate
}
