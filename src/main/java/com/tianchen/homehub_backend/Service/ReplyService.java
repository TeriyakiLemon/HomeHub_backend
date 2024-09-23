package com.tianchen.homehub_backend.Service;

import com.tianchen.homehub_backend.model.Reply;
import com.tianchen.homehub_backend.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public void createReply(Reply reply) {
        replyRepository.save(reply);
    }

    public List<Reply> getRepliesByDiscussionId(Long discussionId) {
        return replyRepository.findByDiscussionId(discussionId);
    }

    public void deleteReply(Long id) {
        replyRepository.deleteById(id);
    }

    public int countUnreadReplies(String username) {
        return replyRepository.countUnreadReplies(username);
    }

    public void markAsRead(String username) {
        replyRepository.markAsRead(username);
    }

}
