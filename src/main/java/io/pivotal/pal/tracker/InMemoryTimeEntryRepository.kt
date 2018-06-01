package io.pivotal.pal.tracker

import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicLong

@Component
class InMemoryTimeEntryRepository : TimeEntryRepository {
    private val map = mutableMapOf<Long, TimeEntry>()
    private val idCounter: AtomicLong = AtomicLong(0)

    override fun find(id: Long): TimeEntry? {
        return map[id]
    }

    override fun list(): List<TimeEntry> {
        return map.values.map { x -> x }
    }

    override fun update(id: Long, timeEntry: TimeEntry): TimeEntry? {
        if (map[id] == null) {
            return null
        }
        val newTimeEntry = TimeEntry(id, timeEntry.projectId, timeEntry.userId, timeEntry.date, timeEntry.hours)
        map[newTimeEntry.id] = newTimeEntry
        return newTimeEntry
    }

    override fun delete(id: Long): TimeEntry? {
        return map.remove(id)
    }

    override fun create(timeEntry: TimeEntry): TimeEntry? {
        val newTimeEntry = TimeEntry(
                idCounter.incrementAndGet(),
                timeEntry.projectId,
                timeEntry.userId,
                timeEntry.date,
                timeEntry.hours)
        map[newTimeEntry.id] = newTimeEntry
        return newTimeEntry
    }
}