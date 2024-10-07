package me.choicore.samples.pms.charge

import me.choicore.samples.pms.context.Timeline

data class ChargingOption(
    val method: ChargingMethod,
    val timeline: Timeline,
    val rateUnit: Double,
)
