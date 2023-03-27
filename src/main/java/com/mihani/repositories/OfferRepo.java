package com.mihani.repositories;

import com.mihani.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OfferRepo extends JpaRepository<Offer, Long> {
}
