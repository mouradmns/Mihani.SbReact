package com.mihani.rest;

import com.mihani.entities.Comment;
import com.mihani.dtos.CommentModel;
import com.mihani.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('BRICOLEUR','CLIENT')")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PreAuthorize("hasAnyAuthority('bricoleur:read','client:read','admin:read')")
    @GetMapping("/comments")
    public List<Comment> findAllComments() throws Exception {
        return commentService.findAllComments();
    }

    @PreAuthorize("hasAnyAuthority('bricoleur:create')")
    @PostMapping("/comments")
    public CommentModel saveComment(@RequestBody CommentModel model) throws Exception {
        Comment comment = Comment.builder()
                .dateComment(model.getDateComment())
                .body(model.getBody())
                .build();
        return commentService.addComment(model.getIdAnnouncement(),model.getIdUser(), comment);
    }

    @PreAuthorize("hasAnyAuthority('bricoleur:update')")
    @PutMapping("/comments")
    public CommentModel modifyComment(@RequestBody CommentModel model) throws Exception {
        Comment comment = Comment.builder()
                .id(model.getId())
                .body(model.getBody())
                .dateComment(model.getDateComment())
                .build();
        return commentService.modifyComment(model.getIdUser(), model.getIdAnnouncement(), comment);
    }

    @PreAuthorize("hasAnyAuthority('bricoleur:delete','admin:delete')")
    @DeleteMapping("/comments")
    public void deleteComment(@RequestBody CommentModel model) throws Exception {
        commentService.deleteComment(model.getIdUser(), model.getId());
    }

}
