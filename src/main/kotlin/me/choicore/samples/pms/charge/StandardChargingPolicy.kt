package me.choicore.samples.pms.charge

import java.time.DayOfWeek
import java.time.LocalDate

class StandardChargingPolicy(
    override val name: String,
    policies: List<DayOfWeekChargingPolicy>,
) : ChargingPolicy {
    init {
        require(policies.isNotEmpty()) { "At least one policy is required" }
    }

    private val registry: Map<DayOfWeek, DayOfWeekChargingPolicy> = policies.associateBy { it.dayOfWeek }

    override fun satisfiedBy(date: LocalDate): Boolean {
        return (registry[date.dayOfWeek] ?: return false).satisfiedBy(date)
    }
}
