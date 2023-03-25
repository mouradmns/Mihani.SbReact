package com.mihani.services;

import com.mihani.entities.Announcement;
import com.mihani.entities.Offer;
import com.mihani.repositories.AnnouncementRepo;
import com.mihani.repositories.OfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfferService {

    @Autowired
    private OfferRepo offerRepo;

    @Autowired
    private AnnouncementRepo announcementRepo;

    public Offer addOffer(Long idAnnouncement, Offer offer) throws Exception {
        Optional<Announcement> optionalAnnouncement = announcementRepo.findById(idAnnouncement);

        if(optionalAnnouncement.isPresent()) {
            Announcement announcement = optionalAnnouncement.get();
            offer.setAnnouncement(announcement);
            return offerRepo.save(offer);
        }
        throw new Exception("There is no announcement with the id " + idAnnouncement);
    }

}
