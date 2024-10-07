package me.choicore.samples.pms.context

class Timeline {
    private val _slots: MutableList<TimeSlot> = mutableListOf()
    val slots: List<TimeSlot> get() = _slots.toList()

    fun add(slot: TimeSlot): Timeline {
        require(canAddSlot(slot)) { "TimeSlot is overlap" }
        _slots.add(slot)
        return this
    }

    private fun canAddSlot(newSlot: TimeSlot): Boolean = _slots.none { it.isOverlap(newSlot) }

    companion object {
        fun create(vararg slots: TimeSlot): Timeline =
            Timeline().apply {
                slots.forEach { add(it) }
            }
    }
}
