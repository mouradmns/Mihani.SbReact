package com.mihani.rest;

import com.mihani.Exceptions.AnnounceNotFoundException;
import com.mihani.dtos.BricoleurProfileDto;
import com.mihani.dtos.UserOffersDto;
import com.mihani.entities.Bricoleur;
import com.mihani.exceptions.BricoleurNotFoundException;
import com.mihani.exceptions.OfferNotFoundException;
import com.mihani.exceptions.UserNotFoundException;
import com.mihani.entities.Offer;
import com.mihani.dtos.OfferModel;
import com.mihani.services.OfferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PutMapping(value = "offers/{id}")
    public ResponseEntity<Offer> updateBricoleur(@PathVariable Long id , @RequestBody OfferModel offerModel) throws OfferNotFoundException {

        Offer offer = Offer.builder()
                .id(offerModel.getId())
                .dateOffer(offerModel.getDateOffer())
                .price(offerModel.getPrice())
                .description(offerModel.getDescription())
                .build();
        Offer updatedOffer=offerServiceImpl.updateOffer(offer);
        return new ResponseEntity<>(updatedOffer, HttpStatus.OK);
    }

    @DeleteMapping("bricoleurs/{id}")
    public void deleteBricoleur(@PathVariable Long id) throws  OfferNotFoundException {
        offerServiceImpl.deleteOffer(id);

    }






    @GetMapping("offers/announcement/{announcementId}")
        public List<UserOffersDto> listOffersByAnnouncement(@PathVariable Long announcementId) throws AnnounceNotFoundException {
        return offerServiceImpl.listAnnouncementOffers(announcementId);
        }


    @GetMapping("offers/user/{userId}")
    public List<UserOffersDto> listOffersByUser(@PathVariable Long userId) throws UserNotFoundException {
        return offerServiceImpl.listBricoleurOffers(userId);
    }


}
