package com.mihani.rest;

import com.mihani.Exceptions.AnnounceNotFoundException;
import com.mihani.Exceptions.UserNotFoundException;
import com.mihani.entities.Offer;
import com.mihani.dtos.OfferModel;
import com.mihani.services.OfferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OfferController {

    @Autowired
    private OfferServiceImpl offerServiceImpl;

    @PostMapping("/offers")
    public Offer addOffer(@RequestBody OfferModel model) throws UserNotFoundException, AnnounceNotFoundException {
        Offer offer = Offer.builder()
                .dateOffer(model.getDateOffer())
                .price(model.getPrice())
                .description(model.getDescription())
                .build();
        return offerServiceImpl.addOffer(model.getIdAnnouncement(), model.getIdUser(),offer);
    }


    @GetMapping("offers/{announcementId}")
        public List<Offer> listOffersByAnnouncement(@PathVariable Long announcementId) throws AnnounceNotFoundException {
        return offerServiceImpl.listAnnouncementOffers(announcementId);
        }


    @GetMapping("offers/{userId}")
    public List<Offer> listOffersByUser(@PathVariable Long userId) throws UserNotFoundException {
        return offerServiceImpl.listBricoleurOffers(userId);
    }


}
