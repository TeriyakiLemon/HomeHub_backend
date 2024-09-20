package com.tianchen.homehub_backend.repository;

import com.tianchen.homehub_backend.model.Reply;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ReplyRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReplyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Reply> replyRowMapper = new RowMapper<>() {
        @Override
        public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Reply(
                    rs.getLong("id"),
                    rs.getLong("discussion_id"),
                    rs.getString("content"),
                    rs.getString("author"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            );
        }
    };

    public int save(Reply reply) {
        String sql = "INSERT INTO replies (discussion_id, content, author, created_at, updated_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        return jdbcTemplate.update(sql, reply.discussionId(), reply.content(), reply.author());
    }

    //根据讨论id 查询所有回复
    public List<Reply> findByDiscussionId(Long discussionId) {
        String sql = "SELECT * FROM replies WHERE discussion_id = ?";
        return jdbcTemplate.query(sql, replyRowMapper, discussionId);
    }

    //删除回复
    public int deleteById(Long id) {
        String sql = "DELETE FROM replies WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }


}
