package io.pivotal.pal.tracker;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.sql.DataSource;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    public JdbcTimeEntryRepository(DataSource dataSource) {
    }

    @NotNull
    @Override
    public TimeEntry create(@NotNull TimeEntry timeEntry) {
        return null;
    }

    @Nullable
    @Override
    public TimeEntry find(long id) {
        return null;
    }

    @NotNull
    @Override
    public List<TimeEntry> list() {
        return null;
    }

    @Nullable
    @Override
    public TimeEntry update(long id, @NotNull TimeEntry timeEntry) {
        return null;
    }

    @Nullable
    @Override
    public TimeEntry delete(long id) {
        return null;
    }
}
