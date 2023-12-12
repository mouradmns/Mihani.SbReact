package com.mihani.services;

import com.mihani.config.BackendURL;
import com.mihani.dtos.AnnouncementDto;
import com.mihani.entities.*;
import com.mihani.mappers.AnnouncementMapper;
import com.mihani.repositories.AnnouncementAttachmentRepo;
import com.mihani.repositories.AnnouncementRepo;
import com.mihani.repositories.UserRepo;
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
    private AnnouncementMapper announcementMapper = new AnnouncementMapper();

    @Autowired
    private UserRepo userRepo;

    public Announcement save(AnnouncementDto dto, MultipartFile[] files) throws Exception {
        List<AnnouncementAttachment> attachments = new ArrayList<>();
        Announcement announcement = announcementMapper.toAnnouncement(dto);
        Optional<User> optionalUser = userRepo.findById(dto.getIdUser());
        if (announcement.getDateAnnouncement().isBefore(announcement.getAppropriateDate())) {
            if (optionalUser.isPresent()) {
                if (files != null) {
                    saveFiles(files, attachments);
                    announcement.setAnnouncementAttachments(attachments);
                }
                announcement.setId(0L);
                announcement.setUser(optionalUser.get());
                announcement = announcementRepo.save(announcement);
                if (attachments != null)
                    for (AnnouncementAttachment announcementAttachment : attachments) {
                        announcementAttachment.setAnnouncement(announcement);
                        announcementAttachmentRepo.save(announcementAttachment);
                    }
                return announcement;
            } else
                throw new Exception("The user doesn't exist");
        } else
            throw new Exception("The appropriate date must be after the current date");
    }


    public AnnouncementDto update(AnnouncementDto dto, MultipartFile[] files) throws Exception {
        Optional<Announcement> existingAnnouncement = announcementRepo.findById(dto.getId());
        Optional<User> optionalUser = userRepo.findById(dto.getIdUser());
        List<AnnouncementAttachment> attachments = new ArrayList<>();
        if (existingAnnouncement.isPresent()) {
            Announcement announcement = existingAnnouncement.get();
            if (existingAnnouncement.get().isAvailable()) {
                if (optionalUser.isPresent()) {
                    if (files != null) {
                        saveFiles(files, attachments);
                        announcement.getAnnouncementAttachments().addAll(attachments);
                    }
                    announcement.setId(dto.getId());
                    announcement.setUser(optionalUser.get());
                    if (!dto.getDescription().isBlank())
                        announcement.setDescription(dto.getDescription());
                    announcement = announcementRepo.save(announcement);
                    if (attachments != null)
                        for (AnnouncementAttachment announcementAttachment : attachments) {
                            announcementAttachment.setAnnouncement(announcement);
                            announcementAttachmentRepo.save(announcementAttachment);
                        }
                    return announcementMapper.toAnnouncementDto(announcement);
                } else
                    throw new Exception("The user doesn't exist");
            }
        }
        throw new Exception("There is no announcement with this id " + dto.getId() + " to modify");
    }

    public void deleteById(Long idAnnouncement, Long idUser) throws Exception {
        if (announcementRepo.findById(idAnnouncement).isPresent()) {
            announcementRepo.deleteById(idAnnouncement);
        } else
            throw new Exception("There is no announcement with this id " + idAnnouncement + " to modify");
    }

    public AnnouncementDto findById(Long id) throws Exception {
        Optional<Announcement> announcement = announcementRepo.findById(id);
        if (announcement.isPresent()) {
            return announcementMapper.toAnnouncementDto(announcement.get());
        } else throw new Exception("The announcement doesn't found");
    }

    public List<AnnouncementDto> findAvailableAnnouncementByFilter(String title, List<BricolageService> types, Cities city) {
        Specification<Announcement> specification = Specification.where(AnnouncementRepo.availabale(true)).and(AnnouncementRepo.validated(true));
        Specification<Announcement> titleSpec = null;
        Specification<Announcement> typeSpec = null;
        Specification<Announcement> citySpec = null;
        if (title != null)
            titleSpec = AnnouncementRepo.titleContains(title);
        if (types != null && !types.isEmpty())
            typeSpec = AnnouncementRepo.typeIn(types);
        if (city != null)
            citySpec = AnnouncementRepo.cityEquals(city);

        if (titleSpec != null)
            specification = specification.and(titleSpec);
        if (typeSpec != null)
            specification = specification.and(typeSpec);
        if (citySpec != null)
            specification = specification.and(citySpec);

        List<Announcement> announcements = announcementRepo.findAll(specification);
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        announcements.forEach(announcement -> {
            announcementDtos.add(announcementMapper.toAnnouncementDto(announcement));
        });

        return announcementDtos;
    }

    public List<AnnouncementDto> findAllAnnouncementsByAvailable(boolean available) {
        Specification<Announcement> specification = Specification.where(AnnouncementRepo.availabale(available));
        List<Announcement> announcements = announcementRepo.findAll(specification);
        List<AnnouncementDto> dtos = new ArrayList<>();
        announcements.forEach(announcement -> dtos.add(announcementMapper.toAnnouncementDto(announcement)));
        return dtos;
    }

    public List<AnnouncementDto> findAllAnnouncementsByUser(Long idUser) {
        List<Announcement> announcements = announcementRepo.findAnnouncementsByUserId(idUser);
        List<AnnouncementDto> dtos = new ArrayList<>();
        announcements.forEach(announcement -> dtos.add(announcementMapper.toAnnouncementDto(announcement)));
        return dtos;
    }

    public List<AnnouncementDto> findNonValidatedAnnouncements() {
        Specification<Announcement> specification = Specification.where(AnnouncementRepo.validated(false));
        List<Announcement> announcements = announcementRepo.findAll(specification);
        List<AnnouncementDto> dtos = new ArrayList<>();
        announcements.forEach(announcement -> dtos.add(announcementMapper.toAnnouncementDto(announcement)));
        return dtos;
    }

    public void acceptAnnouncement(Long idAnnouncement) throws Exception {
        Optional<Announcement> announcement = announcementRepo.findById(idAnnouncement);
        if (announcement.isPresent()) {
            announcement.get().setValidated(true);
            announcementRepo.save(announcement.get());
        } else throw new Exception("The announcement doesn't found");
    }

    private void saveFiles(MultipartFile[] files, List<AnnouncementAttachment> attachments) throws IOException {
        for (MultipartFile file : files) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            String path = backendURL.getBackendURL() + "/images/" + filename;
            AnnouncementAttachment attachment = AnnouncementAttachment.builder()
                    .path(path)
                    .build();
            attachments.add(attachment);
            Path uploadPath = Paths.get("src/main/resources/public/images");
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
    }

}
