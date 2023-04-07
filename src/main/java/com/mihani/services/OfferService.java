package com.mihani.services;


import com.mihani.Exceptions.AnnounceNotFoundException;
import com.mihani.Exceptions.UserNotFoundException;
import com.mihani.entities.Offer;

import java.util.List;

public interface OfferService {
    public Offer addOffer(Long idAnnouncement, Long idUser, Offer offer) throws UserNotFoundException, AnnounceNotFoundException;

    public List<Offer>  listAnnouncementOffers(Long idAnnouncement) throws AnnounceNotFoundException;
    public List<Offer>  listBricoleurOffers(Long idUser) throws UserNotFoundException;
}
