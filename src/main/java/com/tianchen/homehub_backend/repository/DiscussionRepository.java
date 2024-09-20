package com.tianchen.homehub_backend.repository;

import com.tianchen.homehub_backend.model.Discussion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DiscussionRepository {

    private final JdbcTemplate jdbcTemplate;

    public DiscussionRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Discussion> discssionRowMapper = new RowMapper<>() {
        @Override
        public Discussion mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Discussion(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("author"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        }
    };

    public int save(Discussion discussion) {
        String sql = "INSERT INTO discussion (title, content, author, created_at, updated_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        return jdbcTemplate.update(sql, discussion.title(), discussion.content(), discussion.author());
    }

    public Discussion findById(Long id) {
        String sql = "SELECT * FROM discussion WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, discssionRowMapper, id);
    }

    public List<Discussion> findAll() {
        String sql = "SELECT * FROM discussion";
        return jdbcTemplate.query(sql, discssionRowMapper);
    }

    public int update(Discussion discussion) {
        String sql = "UPDATE discussion SET title = ?, content = ?, updated_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql, discussion.title(), discussion.content(), discussion.updatedAt(), discussion.id());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM discussion WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
