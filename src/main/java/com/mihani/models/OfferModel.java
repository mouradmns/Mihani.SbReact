package com.mihani.models;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OfferModel {

    private Long id;
    private LocalDate dateOffer;
    private Double price;
    private Long idAnnouncement;
}
