package com.tianchen.homehub_backend.repository;

import com.tianchen.homehub_backend.model.Message;
import com.tianchen.homehub_backend.model.User;
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

    private  static final String SQL_INSERT_MESSAGE = "INSERT INTO messages (sender_id, receiver_id, content, timestamp, is_read) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_MESSAGES_BETWEEN_USERS_SQL = "SELECT * FROM messages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY timestamp ASC";
    private static final String MARK_MESSAGE_AS_READ_SQL = "UPDATE messages SET is_read = true WHERE id = ?";

    public void saveMessage(Message message) {
        jdbcTemplate.update(SQL_INSERT_MESSAGE,
                message.sender().id(),
                message.receiver().id(),
                message.content(),
                message.timestamp(),
                message.isRead());
    }

    public List<Message> findMessagesBetweenUsers(Long userId1, Long userId2) {
        return jdbcTemplate.query(SELECT_MESSAGES_BETWEEN_USERS_SQL, new MessageRowMapper(), userId1, userId2, userId2, userId1);
    }

    public void markMessageAsRead(Long messageId) {
        jdbcTemplate.update(MARK_MESSAGE_AS_READ_SQL, messageId);
    }

    private class MessageRowMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long senderId = rs.getLong("sender_id");
            Long receiverId = rs.getLong("receiver_id");

            User sender = userRepository.findById(rs.getLong("sender_id")).orElse(null);
            User receiver = userRepository.findById(rs.getLong("receiver_id")).orElse(null);
            return new Message(
                    rs.getLong("id"),
                    sender,
                    receiver,
                    rs.getString("content"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getBoolean("is_read")
            );
        }
    }

}