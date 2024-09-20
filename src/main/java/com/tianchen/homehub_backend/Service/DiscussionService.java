package com.tianchen.homehub_backend.Service;

import com.tianchen.homehub_backend.model.Discussion;
import com.tianchen.homehub_backend.repository.DiscussionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussionService {
    private final DiscussionRepository discussionRepository;

    public DiscussionService(DiscussionRepository discussionRepository) {
        this.discussionRepository = discussionRepository;
    }

    public void createdDiscussion(Discussion discussion) {
        discussionRepository.save(discussion);
    }

    public Discussion getDiscussion(Long id) {
        return discussionRepository.findById(id);
    }

    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findAll();
    }

    public void updateDiscussion(Long id, Discussion discussion) {
        Discussion existingDiscussion = discussionRepository.findById(id);
        if (existingDiscussion != null) {
            Discussion updatedDiscussion = new Discussion(
                    existingDiscussion.id(),
                    discussion.title(),
                    discussion.content(),
                    existingDiscussion.author(),
                    existingDiscussion.createdAt(),
                    discussion.updatedAt()
            );
            discussionRepository.update(discussion);

        }
    }

    public void deleteDiscussion(Long id) {
        discussionRepository.deleteById(id);
    }
}
