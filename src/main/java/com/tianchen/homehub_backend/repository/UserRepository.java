package com.tianchen.homehub_backend.repository;

import com.tianchen.homehub_backend.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveUser(User user) {
        String sql = "INSERT INTO users (community_name, email, username, password, user_type) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, user.communityName(), user.email(), user.username(), user.password(), user.userType());
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> new User(
                    rs.getLong("id"),
                    rs.getString("community_name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("user_type")
            )));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> new User(
                    rs.getLong("id"),
                    rs.getString("community_name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("user_type")
            )));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new User(
                    rs.getLong("id"),
                    rs.getString("community_name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("user_type")
            )));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<User> findUserByCommunityName(String communityName) {
        String sql = "SELECT * FROM users WHERE community_name = ?";
        return jdbcTemplate.query(sql, new Object[]{communityName}, (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("community_name"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("user_type")
        ));
    }
}
