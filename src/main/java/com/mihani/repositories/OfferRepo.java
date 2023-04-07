package com.mihani.repositories;

import com.mihani.entities.Offer;
import com.mihani.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OfferRepo extends JpaRepository<Offer, Long> {
    public List<Offer> getOffersByAnnouncement_Id(Long announcementId);

    public List<Offer> getOffersByUser(User user);
}
