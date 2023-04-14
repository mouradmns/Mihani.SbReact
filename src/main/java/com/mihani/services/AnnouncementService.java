package com.mihani.services;

import com.mihani.config.BackendURL;
import com.mihani.entities.Announcement;
import com.mihani.entities.AnnouncementAttachment;
import com.mihani.entities.BricolageService;
import com.mihani.repositories.AnnouncementAttachmentRepo;
import com.mihani.repositories.AnnouncementRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnnouncementService {

    @Autowired
    private AnnouncementRepo announcementRepo;

    @Autowired
    private AnnouncementAttachmentRepo announcementAttachmentRepo;

    @Autowired
    private BackendURL backendURL;

    public Announcement save(Announcement announcement, MultipartFile[] files) throws Exception {
        List<AnnouncementAttachment> attachments = new ArrayList<>();
        if (announcement.getDateAnnouncement().isBefore(announcement.getAppropriateDate())) {
            for (MultipartFile file : files) {
                String filename = StringUtils.cleanPath(file.getOriginalFilename());
                String path = backendURL.getBackendURL() + "/" + filename;
                AnnouncementAttachment attachment = AnnouncementAttachment.builder()
                        .path(path)
                        .build();
                attachments.add(attachment);
                Path uploadPath = Paths.get("D:\\", "images");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try (InputStream inputStream = file.getInputStream()) {
                    Path filePath = uploadPath.resolve(filename);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new IOException("Could not store file " + filename + ". Please try again!", e);
                }
            }
            announcement.setAnnouncementAttachments(attachments);
            announcement.setId(0L);
            announcement = announcementRepo.save(announcement);
            for (AnnouncementAttachment announcementAttachment : attachments) {
                announcementAttachment.setAnnouncement(announcement);
                announcementAttachmentRepo.save(announcementAttachment);
            }
            return announcement;
        } else
            throw new Exception("The appropriate date must be after the current date");
    }

    public Announcement update(Announcement announcement) throws Exception {
        Optional<Announcement> existingAnnouncement = announcementRepo.findById(announcement.getId());
        if (existingAnnouncement.isPresent()) {
            if (existingAnnouncement.get().isAvailable()) {
                announcementRepo.save(announcement);
                return announcement;
            }
        }
        throw new Exception("Thre is no announcement with this id " + announcement.getId() + " to modify");
    }

    public void deleteById(Long id) throws Exception {
        if (announcementRepo.findById(id).isPresent()) {
            announcementRepo.deleteById(id);
        } else
            throw new Exception("Thre is no announcement with this id " + id + " to modify");
    }

    public Announcement findById(Long id) throws Exception {
        Optional<Announcement> announcement = announcementRepo.findById(id);
        if (announcement.isPresent())
            return announcement.get();
        else throw new Exception("The announcement doesn't found");
    }

    public List<Announcement> findAvailableAnnouncementByFilter(String title, List<BricolageService> types) {
        Specification<Announcement> specification = Specification.where(AnnouncementRepo.isAvailabale());
        Specification<Announcement> titleSpec = null;
        Specification<Announcement> typeSpec = null;
        if (title != null)
            titleSpec = AnnouncementRepo.titleContains(title);
        if (types != null && !types.isEmpty())
            typeSpec = AnnouncementRepo.typeIn(types);

        if (titleSpec != null && typeSpec != null)
            specification = specification.and(titleSpec).and(typeSpec);
        else if (titleSpec != null)
            specification = specification.and(titleSpec);
        else
            specification = specification.and(typeSpec);

        return announcementRepo.findAll(specification);
    }

}
