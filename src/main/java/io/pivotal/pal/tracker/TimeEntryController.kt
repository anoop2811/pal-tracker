package io.pivotal.pal.tracker

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.metrics.CounterService
import org.springframework.boot.actuate.metrics.GaugeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/time-entries")
class TimeEntryController @Autowired constructor(
        private var timeEntryRepository: TimeEntryRepository,
        private val counter: CounterService,
        private val gauge: GaugeService) {

    @PostMapping
    fun create(@RequestBody timeEntry: TimeEntry): ResponseEntity<TimeEntry> {
        counter.increment("TimeEntry.created")
        gauge.submit("timeEntries.count", timeEntryRepository.list().size.toDouble())
        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntryRepository.create(timeEntry))
    }

    @GetMapping("{id}")
    fun read(@PathVariable("id") id: Long): ResponseEntity<TimeEntry> {
        val timeEntry = timeEntryRepository.find(id)

        return if (timeEntry != null) {
            counter.increment("TimeEntry.read")
            ResponseEntity.ok().body(timeEntry)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun list(): ResponseEntity<List<TimeEntry>> {
        counter.increment("TimeEntry.listed")
        return ResponseEntity.ok().body(timeEntryRepository.list())
    }

    @PutMapping("{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody timeEntry: TimeEntry): ResponseEntity<TimeEntry> {
        val updatedTimeEntry: TimeEntry? = timeEntryRepository.update(id, timeEntry)
                ?: return ResponseEntity.notFound().build()
        counter.increment("TimeEntry.updated")
        return ResponseEntity.ok().body(updatedTimeEntry)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<TimeEntry> {
        counter.increment("TimeEntry.deleted")
        gauge.submit("timeEntries.count", timeEntryRepository.list().size.toDouble())


        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(timeEntryRepository.delete(id))
    }
}