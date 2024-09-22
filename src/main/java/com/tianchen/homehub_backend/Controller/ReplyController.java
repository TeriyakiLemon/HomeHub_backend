package com.tianchen.homehub_backend.Controller;

import com.tianchen.homehub_backend.Service.ReplyService;
import com.tianchen.homehub_backend.model.Reply;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies")
public class ReplyController {
    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    //创建回复
    @PostMapping("/create")
    public ResponseEntity<Void> createReply(@RequestBody Reply reply){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();

        Reply newReply = new Reply(null, reply.discussionId(), reply.content(), currentUser, null, null);
        replyService.createReply(newReply);
        return ResponseEntity.ok().build();
    }

    //根据讨论id 查询所有回复
    @GetMapping ("/getRepliesByDiscussionId/{discussionId}")
    public ResponseEntity<List<Reply>> getReplies(@PathVariable Long discussionId) {
        return ResponseEntity.ok(replyService.getRepliesByDiscussionId(discussionId));
    }

    //删除回复
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long id) {
        replyService.deleteReply(id);
        return ResponseEntity.ok().build();
    }




}
