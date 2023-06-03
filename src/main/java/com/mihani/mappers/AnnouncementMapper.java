package com.mihani.mappers;

import com.mihani.dtos.AnnouncementDto;
import com.mihani.entities.Announcement;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementMapper {

    public AnnouncementDto toAnnouncementDto(Announcement announcement) {
        return AnnouncementDto.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .description(announcement.getDescription())
                .appropriateDate(announcement.getAppropriateDate())
                .dateAnnouncement(announcement.getDateAnnouncement())
                .announcementAttachments(announcement.getAnnouncementAttachments())
                .available(announcement.isAvailable())
                .validated(announcement.getValidated())
                .typeService(announcement.getTypeService())
                .city(announcement.getCity())
                .idUser(announcement.getUser().getId())
                .username(announcement.getUser().getNom() + " " + announcement.getUser().getPrenom())
                .userProfileImage(announcement.getUser().getMainPic())
                .build();
    }

    public Announcement toAnnouncement(AnnouncementDto announcementDto) {
        return Announcement.builder()
                .id(announcementDto.getId())
                .title(announcementDto.getTitle())
                .description(announcementDto.getDescription())
                .appropriateDate(announcementDto.getAppropriateDate())
                .dateAnnouncement(announcementDto.getDateAnnouncement())
                .announcementAttachments(announcementDto.getAnnouncementAttachments())
                .typeService(announcementDto.getTypeService())
                .city(announcementDto.getCity())
                .available(announcementDto.getAvailable())
                .validated(announcementDto.getValidated())
                .build();
    }

}
