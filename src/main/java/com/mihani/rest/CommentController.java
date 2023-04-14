package com.mihani.rest;

import com.mihani.entities.Comment;
import com.mihani.dtos.CommentModel;
import com.mihani.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    public Comment saveComment(@RequestBody CommentModel model) throws Exception {
        Comment comment = Comment.builder()
                .dateComment(model.getDateComment())
                .body(model.getBody())
                .build();
        return commentService.addComment(model.getIdAnnouncement(),model.getIdUser(), comment);
    }

    @PutMapping("/comments")
    public Comment modifyComment(@RequestBody CommentModel model) throws Exception {
        Comment comment = Comment.builder()
                .id(model.getId())
                .body(model.getBody())
                .dateComment(model.getDateComment())
                .build();
        return commentService.modifyComment(model.getIdUser(), model.getIdAnnouncement(), comment);
    }

    @DeleteMapping("/comments")
    public void deleteComment(@RequestBody CommentModel model) throws Exception {
        commentService.deleteComment(model.getIdUser(), model.getId());
    }

}
