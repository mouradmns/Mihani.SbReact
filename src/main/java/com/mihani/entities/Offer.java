package com.mihani.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "offer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "offer_description")
    private String description;

    @Column(name = "date_offer")
    private LocalDate dateOffer;

    @Column(name = "price")
    private Double price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_announcement")
    @JsonBackReference
    private Announcement announcement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private Bricoleur bricoleur;





}
    