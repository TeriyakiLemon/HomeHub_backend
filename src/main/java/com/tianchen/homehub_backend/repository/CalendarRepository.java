package com.tianchen.homehub_backend.repository;

import com.tianchen.homehub_backend.model.Calendar;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CalendarRepository {
    private final JdbcTemplate jdbcTemplate;

    public CalendarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveCalendar(Calendar calendar) {
        String sql = "INSERT INTO calendar (date, time, title) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, calendar.date(), calendar.time(), calendar.title());
    }

    public Optional<Calendar> findById(Long id) {
        String sql = "SELECT * FROM calendar WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new Calendar(
                    rs.getLong("id"),
                    rs.getString("date"),
                    rs.getString("time"),
                    rs.getString("title")
            )));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Calendar> findAll() {
        String sql = "SELECT * FROM calendar";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Calendar(
                rs.getLong("id"),
                rs.getString("date"),
                rs.getString("time"),
                rs.getString("title")
        ));
    }

    public List<Calendar> findByDate(String date) {
        String sql = "SELECT * FROM calendar WHERE date = ?";
        return jdbcTemplate.query(sql, new Object[]{date}, (rs, rowNum) -> new Calendar(
                rs.getLong("id"),
                rs.getString("date"),
                rs.getString("time"),
                rs.getString("title")
        ));
    }
}
