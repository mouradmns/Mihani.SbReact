package com.mihani.rest;

import com.mihani.entities.Comment;
import com.mihani.dtos.CommentModel;
import com.mihani.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        return commentService.addComment(model.getIdAnnouncement(), comment);
    }

}
