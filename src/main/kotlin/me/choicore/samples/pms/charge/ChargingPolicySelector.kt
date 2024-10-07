package me.choicore.samples.pms.charge

import java.time.LocalDate

class ChargingPolicySelector(
    private val policies: List<ChargingPolicy>,
) {
    fun select(date: LocalDate): ChargingPolicy =
        policies.firstOrNull { it.satisfiedBy(date) } ?: throw IllegalArgumentException("No policy satisfied by $date")
}
