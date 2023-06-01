package com.mihani.rest;

import com.mihani.dtos.AnnouncementDto;
import com.mihani.entities.Comment;
import com.mihani.services.CommentService;
import com.mihani.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/users/{id}/comments")
    public List<Comment> findCommentsByUserId(@PathVariable("id") Long id) {
        return commentService.findCommentsByUserId(id);
    }

    @GetMapping("/users/{id}/announcements")
    public List<AnnouncementDto> findAnnouncementsByUserId(@PathVariable("id") Long id) {
        return announcementService.findAllAnnouncementsByUser(id);
    }

}
