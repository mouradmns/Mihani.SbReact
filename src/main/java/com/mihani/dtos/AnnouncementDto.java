package com.mihani.dtos;

import com.mihani.entities.AnnouncementAttachment;
import com.mihani.entities.BricolageService;
import com.mihani.entities.Cities;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AnnouncementDto {

    private Long id;
    private String title;
    private List<BricolageService> typeService;
    private Cities city;
    private String description;
    private LocalDate appropriateDate;
    private Boolean available;
    private LocalDate dateAnnouncement;
    private List<AnnouncementAttachment> announcementAttachments;
    private Long idUser;
    private String username;
    private String userProfileImage;
}
