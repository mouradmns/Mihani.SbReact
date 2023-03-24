package com.mihani.services;

import com.mihani.entities.Announcement;
import com.mihani.entities.Report;
import com.mihani.repositories.AnnouncementRepo;
import com.mihani.repositories.ReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private AnnouncementRepo announcementRepo;

    public Report addReport(Long idAnnouncement, Report report) throws Exception {
        Optional<Announcement> optionalAnnouncement = announcementRepo.findById(idAnnouncement);

        if(optionalAnnouncement.isPresent()) {
            Announcement announcement = optionalAnnouncement.get();
            report.setAnnouncement(announcement);
            return reportRepo.save(report);
        }
        throw new Exception("There is no announcement with id " + idAnnouncement + " to report it");
    }

}
