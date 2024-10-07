package me.choicore.samples.pms.charge

import java.time.LocalDate

interface ChargingPolicy {
    val name: String

    fun satisfiedBy(date: LocalDate): Boolean
}
