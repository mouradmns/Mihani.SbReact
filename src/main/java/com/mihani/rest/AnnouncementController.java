package com.mihani.rest;

import com.mihani.dtos.AnnouncementDto;
import com.mihani.dtos.CommentModel;
import com.mihani.entities.Announcement;
import com.mihani.entities.BricolageService;
import com.mihani.entities.Cities;
import com.mihani.services.AnnouncementService;
import com.mihani.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('CLIENT')")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private CommentService commentService;

    // the url /announcement?title=title&type=type&city=city to fetch this

    @PreAuthorize("hasAnyAuthority('bricoleur:read')")
    @GetMapping("/announcements")
    public List<AnnouncementDto> findByFilter(@RequestParam(name = "title", required = false) String title,
                                              @RequestParam(name = "type", required = false) String[] type,
                                              @RequestParam(name = "city", required = false) String city) {
        //TODO fix this after when the enum will be fixed
        List<BricolageService> types = Arrays.stream(type).map(BricolageService::valueOf).toList();
        Cities cityEnum = null;
        if (!city.isBlank())
             cityEnum= Cities.valueOf(city);
        return announcementService.findAvailableAnnouncementByFilter(title, types, cityEnum);
    }

    @PreAuthorize("hasAnyAuthority('bricoleur:read','client:read','admin:read')")
    @GetMapping("/announcements/{id}")
    public AnnouncementDto findById(@PathVariable("id") Long id) throws Exception {
        return announcementService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('bricoleur:read','client:read','admin:read')")
    @GetMapping("/announcements/{id}/comments")
    public List<CommentModel> findCommentsByAnnouncementId(@PathVariable("id") Long id) throws Exception {
        return commentService.findCommentsByAnnouncementId(id);
    }


    @PreAuthorize("hasAnyAuthority('client:create')")
    @PostMapping(value = "/announcements", consumes = {"multipart/form-data"})
    public Announcement save(@RequestParam(name = "announcementAttachments", required = false) MultipartFile[] files,
                             @RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("appropriateDate") LocalDate appropriateDate,
                             @RequestParam("typeService") String[] typeService,
                             @RequestParam("city") String city,
                             @RequestParam("idUser") Long idUser) throws Exception {
        List<BricolageService> typeServices = Arrays.stream(typeService).map(BricolageService::valueOf).toList();
        Cities cityEnum = Cities.valueOf(city);
        AnnouncementDto dto = AnnouncementDto.builder()
                .title(title)
                .description(description)
                .idUser(idUser)
                .appropriateDate(appropriateDate)
                .dateAnnouncement(LocalDate.now())
                .typeService(typeServices)
                .city(cityEnum)
                .available(true)
                .validated(false)
                .build();
        return announcementService.save(dto, files);
    }


    @PreAuthorize("hasAnyAuthority('client:update')")
    @PutMapping(value = "/announcements", consumes = {"multipart/form-data"})
    public AnnouncementDto update(@RequestParam(name = "announcementAttachments", required = false) MultipartFile[] files,
                               @RequestParam("id") Long id,
                               @RequestParam("idUser") Long idUser,
                               @RequestParam("description") String description) throws Exception {
        AnnouncementDto announcement = AnnouncementDto.builder()
                .id(id)
                .idUser(idUser)
                .description(description)
                .build();
        return announcementService.update(announcement, files);
    }

    @PreAuthorize("hasAnyAuthority('client:delete','admin:delete')")
    @DeleteMapping("/announcements")
    public void delete(@RequestBody AnnouncementDto dto) throws Exception {
        announcementService.deleteById(dto.getId(), dto.getIdUser());
    }

}
