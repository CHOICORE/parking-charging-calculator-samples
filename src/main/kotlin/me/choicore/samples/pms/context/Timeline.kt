package me.choicore.samples.pms.context

import java.time.LocalTime

class Timeline {
    private val _slots: MutableList<TimeSlot> = mutableListOf()
    val slots: List<TimeSlot> get() = _slots.toList()

    fun add(slot: TimeSlot): Timeline {
        require(this.canAddSlot(slot)) { "TimeSlot is overlap" }
        this._slots.add(slot)
        return this
    }

    fun add(
        startTimeInclusive: LocalTime,
        endTimeInclusive: LocalTime,
    ): Timeline = add(slot = TimeSlot(startTimeInclusive = startTimeInclusive, endTimeInclusive = endTimeInclusive))

    private fun canAddSlot(newSlot: TimeSlot): Boolean = this._slots.none { it.isOverlap(newSlot) }

    companion object {
        fun create(vararg slots: TimeSlot): Timeline =
            Timeline().apply {
                slots.forEach { add(it) }
            }

        val FULL_TIME: Timeline = create(TimeSlot.FULL_TIME)
    }
}
