package com.mihani.mappers;

import com.mihani.dtos.BricoleurProfileDto;
import com.mihani.dtos.OfferModel;
import com.mihani.dtos.UserOffersDto;
import com.mihani.entities.Bricoleur;
import com.mihani.entities.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserOffersMapper {

    public UserOffersDto fromOffer(Offer offer){
        UserOffersDto userOffersDto= new UserOffersDto();
        BeanUtils.copyProperties(offer,userOffersDto);

        userOffersDto.setIdOffer(offer.getId());

        if(offer.getBricoleur() != null){
            userOffersDto.setIdBricoleur(offer.getBricoleur().getId());
            userOffersDto.setNom(offer.getBricoleur().getNom());
            userOffersDto.setPrenom(offer.getBricoleur().getPrenom());
            userOffersDto.setRating(offer.getBricoleur().getRating());

        }

        if(offer.getBricoleur() != null){
            userOffersDto.setAnnouncementId(offer.getAnnouncement().getId());
            userOffersDto.setTitleAnnouncement(offer.getAnnouncement().getTitle());
            userOffersDto.setAnnouncementAvailability(offer.getAnnouncement().getAvailable());
        }

        return userOffersDto;
    }
}
