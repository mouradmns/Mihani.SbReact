package com.mihani.services;

import com.mihani.entities.Announcement;
import com.mihani.repositories.AnnouncementRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepo announcementRepo;

    public Announcement save(Announcement announcement) throws Exception {
        if(announcement.getDateAnnouncement().isBefore(announcement.getAppropriateDate())) {
            announcement.setId(0L);
            announcement = announcementRepo.save(announcement);
            return announcement;
        } else
            throw new Exception("The appropriate date must be after the current date");
    }

    public Announcement update(Announcement announcement) throws Exception {
        Optional<Announcement> existingAnnouncement = announcementRepo.findById(announcement.getId());
        if(existingAnnouncement.isPresent()) {
            if(existingAnnouncement.get().isAvailable()) {
                announcementRepo.save(announcement);
                return announcement;
            }
        }
            throw new Exception("Thre is no announcement with this id " + announcement.getId() + " to modify");
    }

    public void deleteById(Long id) throws Exception {
        if(announcementRepo.findById(id).isPresent()) {
            announcementRepo.deleteById(id);
        } else
            throw new Exception("Thre is no announcement with this id " + id + " to modify");
    }

    public Announcement findById(Long id) throws Exception {
        Optional<Announcement> announcement = announcementRepo.findById(id);
        if(announcement.isPresent())
            return announcement.get();
        else throw new Exception("The announcement doesn't found");
    }

    public List<Announcement> findAvailableAnnouncementByFilter(String title, String type) {
        Specification<Announcement> specification = Specification.where(AnnouncementRepo.isAvailabale());
        Specification<Announcement> titleSpec = null;
        Specification<Announcement> typeSpec = null;
        if(title != null)
            titleSpec = AnnouncementRepo.titleContains(title);
        if(type != null)
            typeSpec = AnnouncementRepo.typeEquals(type);

        if(titleSpec != null && typeSpec != null)
            specification = specification.and(titleSpec).and(typeSpec);
        else if(titleSpec != null)
            specification = specification.and(titleSpec);
        else
            specification = specification.and(typeSpec);

        return announcementRepo.findAll(specification);
    }

}
