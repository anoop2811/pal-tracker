package io.pivotal.pal.tracker

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

@Component
class TimeEntryHealthIndicator @Autowired constructor(private var timeEntryRepository: TimeEntryRepository) : HealthIndicator {
    final val MAX_TIME_ENTRIES = 5

    override fun health(): Health {
        if (timeEntryRepository.list().size < MAX_TIME_ENTRIES) {
            return Health.up().build()
        }
        return Health.down().build()
    }
}