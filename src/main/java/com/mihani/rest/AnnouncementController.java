package com.mihani.rest;

import com.mihani.dtos.CommentModel;
import com.mihani.entities.Announcement;
import com.mihani.entities.BricolageService;
import com.mihani.entities.Comment;
import com.mihani.services.AnnouncementService;
import com.mihani.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private CommentService commentService;

    // the url /announcement?title=title&type=type to fetch this
        @GetMapping("/announcements")
    public List<Announcement> findByFilter(@RequestParam(name = "title", required = false) String title,
                                           @RequestParam(name = "type", required = false) String[] type) {
        //TODO fix this after when the enum will be fixed
        List<BricolageService> types = Arrays.stream(type).map(BricolageService::valueOf).toList();
        return announcementService.findAvailableAnnouncementByFilter(title, types);
    }

    @GetMapping("/announcements/{id}")
    public Announcement findById(@PathVariable("id") Long id) throws Exception {
        return announcementService.findById(id);
    }

    @GetMapping("/announcements/{id}/comments")
    public List<CommentModel> findCommentsByAnnouncementId(@PathVariable("id") Long id) throws Exception {
        return commentService.findCommentsByAnnouncementId(id);
    }

    @PostMapping( value = "/announcements", consumes = {"multipart/form-data"})
    public Announcement save(@RequestParam(name = "announcementAttachments", required = false) MultipartFile[] files,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("appropriateDate") LocalDate appropriateDate,
                             @RequestParam("typeService") String[] typeService) throws Exception {
        List<BricolageService> typeServices = Arrays.stream(typeService).map(BricolageService::valueOf).toList();
        Announcement announcement = Announcement.builder()
                .title(title)
                .description(description)
                .typeService(typeServices)
                .appropriateDate(appropriateDate)
                .dateAnnouncement(LocalDate.now())
                .available(true)
                .build();
        return announcementService.save(announcement, files);
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
