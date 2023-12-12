package com.mihani.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserOffersDto {

    private Long idOffer;
    private String description;
    private LocalDate dateOffer;
    private Double price;

    private Long idBricoleur;
    private String prenom;
    private String nom;
    private Double Rating;


    private Long announcementId;
    private String titleAnnouncement;
    private Boolean announcementAvailability;

    public UserOffersDto(Long idOffer, String description) {
        this.idOffer = idOffer;
        this.description = description;
    }

    public UserOffersDto() {
    }
}
