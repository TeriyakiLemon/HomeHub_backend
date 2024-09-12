package com.tianchen.homehub_backend.Service;

import com.tianchen.homehub_backend.model.Message;
import com.tianchen.homehub_backend.model.User;
import com.tianchen.homehub_backend.repository.MessageRepository;
import com.tianchen.homehub_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public Message sendMessage(Long senderId, Long receiverId, String content){
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> receiver = userRepository.findById(receiverId);

        if (sender.isPresent() && receiver.isPresent()) {
            Message message = new Message(null,
                    sender.get(),
                    receiver.get(),
                    content,
                    LocalDateTime.now(),
                    false);
            messageRepository.saveMessage(message);
            return message;
        } else {
            throw new RuntimeException("Sender or receiver not found");
        }
    }

    public List<Message> getMessages(Long userId1, Long userId2) {
        return messageRepository.findMessagesBetweenUsers(userId1, userId2);
    }

    public void markMessageAsRead(Long messageId) {
        messageRepository.markMessageAsRead(messageId);
    }

}