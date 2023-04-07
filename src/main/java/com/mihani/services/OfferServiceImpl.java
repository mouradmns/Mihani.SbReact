package com.mihani.services;

import com.mihani.Exceptions.AnnounceNotFoundException;
import com.mihani.Exceptions.UserNotFoundException;
import com.mihani.entities.Announcement;
import com.mihani.entities.Offer;
import com.mihani.entities.User;
import com.mihani.repositories.AnnouncementRepo;
import com.mihani.repositories.OfferRepo;
import com.mihani.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepo offerRepo;
    @Autowired
    private AnnouncementRepo announcementRepo;

    @Autowired
    private UserRepo userRepo;

    public Offer addOffer(Long idAnnouncement, Long idUser,Offer offer) throws UserNotFoundException, AnnounceNotFoundException {
        Optional<Announcement> optionalAnnouncement = announcementRepo.findById(idAnnouncement);
        Optional<User> optionalUser =userRepo.findById(idUser);

        if ( optionalUser.isPresent()){
            if(optionalAnnouncement.isPresent()) {
                Announcement announcement = optionalAnnouncement.get();
                offer.setAnnouncement(announcement);

                User user = optionalUser.get();
                offer.setUser(user);

                return offerRepo.save(offer);
            }
            throw new AnnounceNotFoundException("There is no announcement with the id " + idAnnouncement);
        }
        throw new UserNotFoundException("There is no User with the id " +idUser);
    }


    @Override
    public List<Offer> listAnnouncementOffers(Long idAnnouncement) throws AnnounceNotFoundException {

        Optional<Announcement> optionalAnnouncement = announcementRepo.findById(idAnnouncement);
        if(optionalAnnouncement.isPresent()){
            return offerRepo.getOffersByAnnouncement_Id(idAnnouncement);
        }
        throw  new AnnounceNotFoundException("no Announcement found for id " + idAnnouncement);
    }

    @Override
    public List<Offer> listBricoleurOffers(Long idUser) throws UserNotFoundException {
        Optional<User> optionalUser =userRepo.findById(idUser);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
        return offerRepo.getOffersByUser(user);
        }

        throw new UserNotFoundException("User not found for this Id: "+idUser);
    }

}
