package com.mihani.dtos;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OfferModel {

    private Long id;
     private String description;

    private LocalDate dateOffer;
    private Double price;
    private Long idAnnouncement;
    private Long idUser;

}
