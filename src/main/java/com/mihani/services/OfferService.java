package com.mihani.services;


import com.mihani.exceptions.AnnounceNotFoundException;
import com.mihani.dtos.UserOffersDto;
import com.mihani.exceptions.BricoleurNotFoundException;
import com.mihani.exceptions.OfferNotFoundException;
import com.mihani.exceptions.UserNotFoundException;
import com.mihani.entities.Offer;

import java.util.List;

public interface OfferService {
    public Offer addOffer(Long idAnnouncement, Long idUser, Offer offer) throws UserNotFoundException, AnnounceNotFoundException;

    Offer updateOffer(Offer offer) throws BricoleurNotFoundException, OfferNotFoundException;

    void deleteOffer(Long idOffer) throws BricoleurNotFoundException, OfferNotFoundException;

    public List<UserOffersDto>  listAnnouncementOffers(Long idAnnouncement) throws AnnounceNotFoundException;
    public List<UserOffersDto>  listBricoleurOffers(Long idUser) throws UserNotFoundException;
}
