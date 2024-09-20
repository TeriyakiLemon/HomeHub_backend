package com.tianchen.homehub_backend.Controller;


import com.tianchen.homehub_backend.Service.DiscussionService;
import com.tianchen.homehub_backend.model.Discussion;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/discussions")
public class DiscussionController {
    private final DiscussionService discussionService;

    public DiscussionController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @PostMapping("/Create")
    public ResponseEntity<Void> createDiscussion(@RequestBody Discussion discussion) {
        // 从 Spring Security 获取当前登录的用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // 设置 author 为当前登录用户
        discussion = new Discussion(null, discussion.title(), discussion.content(), currentUsername, null, null);

        discussionService.createdDiscussion(discussion);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getDiscssion/{id}")
    public ResponseEntity<Discussion> getDiscussion(@PathVariable Long id) {
        Discussion discussion = discussionService.getDiscussion(id);
        return ResponseEntity.ok(discussion);
    }

    @GetMapping("/getAllDiscussions")
    public ResponseEntity<List<Discussion>> getAllDiscussions() {
        List<Discussion> discussions = discussionService.getAllDiscussions();
        if (discussions.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(discussions);
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<Void> updateDiscussion(@PathVariable Long id, @RequestBody Discussion discussion) {
        discussionService.updateDiscussion(id, discussion);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteDiscussion(@PathVariable Long id) {
        discussionService.deleteDiscussion(id);
        return ResponseEntity.ok().build();
    }
}
