package com.mihani.services;

import com.mihani.exceptions.AnnounceNotFoundException;
import com.mihani.dtos.UserOffersDto;
import com.mihani.exceptions.OfferNotFoundException;
import com.mihani.exceptions.UserNotFoundException;
import com.mihani.entities.Announcement;
import com.mihani.entities.Bricoleur;
import com.mihani.entities.Offer;
import com.mihani.mappers.UserOffersMapper;
import com.mihani.repositories.AnnouncementRepo;
import com.mihani.repositories.BricoleurRepo;
import com.mihani.repositories.OfferRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {

    private OfferRepo offerRepo;
    private AnnouncementRepo announcementRepo;
    private BricoleurRepo bricoleurRepo;

    private UserOffersMapper offMapper;

    public Offer addOffer(Long idAnnouncement, Long idBricoleur, Offer offer) throws UserNotFoundException, AnnounceNotFoundException {
        Optional<Bricoleur> optionalBricoleur =bricoleurRepo.findById(idBricoleur);

        if ( optionalBricoleur.isPresent()){

            Optional<Announcement> optionalAnnouncement = announcementRepo.findById(idAnnouncement);
            if(optionalAnnouncement.isPresent()) {
                Announcement announcement = optionalAnnouncement.get();
                offer.setAnnouncement(announcement);

                Bricoleur bricoleur = optionalBricoleur.get();
                offer.setBricoleur(bricoleur);
                offer.setDateOffer(LocalDate.now());
                return offerRepo.save(offer) ;
            }
                throw new AnnounceNotFoundException("There is no announcement with the id " + idAnnouncement);
        }
        throw new UserNotFoundException("There is no User with the id " +idBricoleur);
    }

    @Override
    public Offer updateOffer(Offer offer) throws  OfferNotFoundException {
        Optional<Offer> existingOffer = offerRepo.findById(offer.getId());
        if(existingOffer.isPresent()){
            return offerRepo.save(offer);
        }else {
            throw new OfferNotFoundException("Offer given not Found!!");
        }

    }

    @Override
    public void deleteOffer(Long idOffer) throws  OfferNotFoundException {
        Optional<Offer> existingOffer = offerRepo.findById(idOffer);

        if(existingOffer.isPresent()) {
            bricoleurRepo.deleteById(idOffer);
        }else {
            throw new OfferNotFoundException("Offer to delete  not found");
        }
    }


    @Override
    public List<UserOffersDto> listAnnouncementOffers(Long idAnnouncement) throws AnnounceNotFoundException {

        Optional<Announcement> optionalAnnouncement = announcementRepo.findById(idAnnouncement);
        if(optionalAnnouncement.isPresent()){

            List<Offer> offers= offerRepo.getOffersByAnnouncement_Id(idAnnouncement);
            List<UserOffersDto> listoffsDto= offers.stream().map(offer ->
                    offMapper.fromOffer(offer)).toList();
            return listoffsDto;
        }
        throw  new AnnounceNotFoundException("no  Announcement found for id " + idAnnouncement);
    }

    @Override
    public List<UserOffersDto> listBricoleurOffers(Long idBricoleur) throws UserNotFoundException {
        Optional<Bricoleur> optionalBricoleur =bricoleurRepo.findById(idBricoleur);
        if(optionalBricoleur.isPresent()) {
            Bricoleur bricoleur = optionalBricoleur.get();

            List<Offer> offers= offerRepo.getOffersByBricoleur(bricoleur);
            List<UserOffersDto> listoffsDto= offers.stream().map(offer ->
                        offMapper.fromOffer(offer)).toList();

        return listoffsDto;
        }

        throw new UserNotFoundException("User not found for this Id: "+idBricoleur);
    }

}
