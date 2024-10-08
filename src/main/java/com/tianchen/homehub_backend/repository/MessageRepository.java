package com.tianchen.homehub_backend.repository;

import com.tianchen.homehub_backend.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MessageRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    private  static final String SQL_INSERT_MESSAGE = "INSERT INTO messages (sender_Username,receiver_Username , content, timestamp, is_read) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_MESSAGES_BETWEEN_USERS_SQL = "SELECT * FROM messages WHERE (sender_Username = ? AND receiver_Username = ?) OR (sender_Username = ? AND receiver_Username = ?) ORDER BY timestamp ASC";
    private static final String MARK_MESSAGE_AS_READ_SQL = "UPDATE messages SET is_read = true WHERE id = ?";

    public void saveMessage(Message message) {
        jdbcTemplate.update(SQL_INSERT_MESSAGE,
                message.senderUsername(),
                message.receiverUsername(),
                message.content(),
                message.timestamp(),
                message.isRead());
    }

    public List<Message> findMessagesBetweenUsers(String username1, String username2) {
        return jdbcTemplate.query(SELECT_MESSAGES_BETWEEN_USERS_SQL, new MessageRowMapper(), username1, username2, username2, username1);
    }

    public void markMessageAsRead(Long messageId) {
        jdbcTemplate.update(MARK_MESSAGE_AS_READ_SQL, messageId);
    }

    public int countUnreadMessages(String username) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM messages WHERE receiver_Username = ? AND is_read = false", Integer.class, username);
    }

    private class MessageRowMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Message(
                    rs.getLong("id"),
                    rs.getString("sender_Username"),
                    rs.getString("receiver_username"),
                    rs.getString("content"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getBoolean("is_read")
            );
        }
    }

}