package io.pivotal.pal.tracker

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class TimeEntryController {
    private var timeEntryRepository: TimeEntryRepository

    @Autowired
    constructor(timeEntryRepository: TimeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository
    }

    @PostMapping("/time-entries")
    fun create(@RequestBody timeEntry: TimeEntry): ResponseEntity<TimeEntry> {
        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntryRepository.create(timeEntry))
    }

    @GetMapping("/time-entries/{id}")
    fun read(@PathVariable("id") id: Long): ResponseEntity<TimeEntry> {
        val timeEntry = timeEntryRepository.find(id)

        if (timeEntry != null) {
            return ResponseEntity.ok().body(timeEntry)
        } else {
            return ResponseEntity.notFound().build()
        }

    }

    @GetMapping("/time-entries")
    fun list(): ResponseEntity<List<TimeEntry>> {
        return ResponseEntity.ok().body(timeEntryRepository.list())
    }

    @PutMapping("/time-entries/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody timeEntry: TimeEntry): ResponseEntity<TimeEntry> {
        var updatedTimeEntry = timeEntryRepository.update(id, timeEntry)
        if (updatedTimeEntry == null) {
            return ResponseEntity.notFound().build()
        }
        return ResponseEntity.ok().body(updatedTimeEntry)
    }

    @DeleteMapping("/time-entries/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<TimeEntry> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(timeEntryRepository.delete(id))
    }
}