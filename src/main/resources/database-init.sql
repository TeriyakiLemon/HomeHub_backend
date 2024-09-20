DROP TABLE IF EXISTS replies;
DROP TABLE IF EXISTS discussion;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS users;



CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       community_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       user_type VARCHAR(50) NOT NULL
);

CREATE TABLE messages (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          sender_username VARCHAR(255) NOT NULL,
                          receiver_username VARCHAR(255) NOT NULL,
                          content TEXT NOT NULL,
                          timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          is_read BOOLEAN DEFAULT FALSE,
                          FOREIGN KEY (sender_username) REFERENCES users(username) ON DELETE CASCADE,
                          FOREIGN KEY (receiver_username) REFERENCES users(username) ON DELETE CASCADE
);

CREATE TABLE discussion (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        content TEXT NOT NULL,
                        author VARCHAR(255) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        FOREIGN KEY (author) REFERENCES users(username) ON DELETE CASCADE


);

CREATE TABLE replies (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         discussion_id BIGINT NOT NULL,
                         content TEXT NOT NULL,
                         author VARCHAR(255) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (discussion_id) REFERENCES discussion(id) ON DELETE CASCADE,
                         FOREIGN KEY (author) REFERENCES users(username) ON DELETE CASCADE
);


