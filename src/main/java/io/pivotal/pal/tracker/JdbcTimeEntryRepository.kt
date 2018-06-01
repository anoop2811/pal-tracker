package io.pivotal.pal.tracker

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.Date
import java.sql.ResultSet
import java.sql.Statement.RETURN_GENERATED_KEYS
import javax.sql.DataSource

class JdbcTimeEntryRepository(dataSource: DataSource) : TimeEntryRepository {
    private val jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource)

    override fun create(timeEntry: TimeEntry): TimeEntry? {
        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update({ connection ->
            val statement = connection.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                            "VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS)
            statement.setLong(1, timeEntry.projectId)
            statement.setLong(2, timeEntry.userId)
            statement.setDate(3, Date.valueOf(timeEntry.date))
            statement.setInt(4, timeEntry.hours)
            statement
        }, keyHolder)

        return find(keyHolder.key?.toLong()!!)
    }

    override fun find(id: Long): TimeEntry? {
        return jdbcTemplate.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
                TimeEntryRowMapper(),
                id).firstOrNull()
    }

    override fun list(): List<TimeEntry> {
        return jdbcTemplate.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries",
                TimeEntryRowMapper())
    }

    override fun update(id: Long, timeEntry: TimeEntry): TimeEntry? {
        jdbcTemplate.update(
                "UPDATE time_entries SET project_id = ?, user_id = ?, date = ?, hours =? WHERE id = ?",
                timeEntry.projectId,
                timeEntry.userId,
                Date.valueOf(timeEntry.date),
                timeEntry.hours,
                id)

        return find(id)
    }

    override fun delete(id: Long): TimeEntry? {
        jdbcTemplate.update("DELETE FROM time_entries where id = ?", id)
        return null
    }

    class TimeEntryRowMapper : RowMapper<TimeEntry> {
        override fun mapRow(rs: ResultSet, rowNum: Int): TimeEntry? {
            return TimeEntry(rs.getLong("id"),
                    rs.getLong("project_id"),
                    rs.getLong("user_id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getInt("hours"))
        }
    }
}
