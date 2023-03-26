package com.mihani.rest;

import com.mihani.entities.Offer;
import com.mihani.dtos.OfferModel;
import com.mihani.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController {

    @Autowired
    private OfferService offerService;

    @PostMapping("/offers")
    public Offer addOffer(@RequestBody OfferModel model) throws Exception {
        Offer offer = Offer.builder()
                .dateOffer(model.getDateOffer())
                .price(model.getPrice())
                .build();
        return offerService.addOffer(model.getIdAnnouncement(), offer);
    }

}
