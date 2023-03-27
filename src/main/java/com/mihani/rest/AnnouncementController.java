package com.mihani.rest;

import com.mihani.entities.Announcement;
import com.mihani.entities.Comment;
import com.mihani.services.AnnouncementService;
import com.mihani.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private CommentService commentService;

    // the url /announcement?title=title&type=type to fetch this
    @GetMapping("/announcements")
    public List<Announcement> findByFilter(@RequestParam(name = "title", required = false) String title,
                                           @RequestParam(name = "type", required = false) String type) {
        return announcementService.findAvailableAnnouncementByFilter(title, type);
    }

    @GetMapping("/announcements/{id}")
    public Announcement findById(@PathVariable("id") Long id) throws Exception {
        return announcementService.findById(id);
    }

    @GetMapping("/announcements/{id}/comments")
    public List<Comment> findCommentsByAnnouncementId(@PathVariable("id") Long id) throws Exception {
        return commentService.findCommentsByAnnouncementId(id);
    }

    @PostMapping("/announcements")
    public Announcement save(@RequestBody Announcement announcement) throws Exception {
        return announcementService.save(announcement);
    }

    @PutMapping("/announcements")
    public Announcement update(@RequestBody Announcement announcement) throws Exception {
        return announcementService.update(announcement);
    }

    @DeleteMapping("/announcements/{id}")
    public void delete(@PathVariable("id") Long id) throws Exception {
        announcementService.deleteById(id);
    }

}
