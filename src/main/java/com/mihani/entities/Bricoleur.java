package com.mihani.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@DiscriminatorValue("BR")

public class Bricoleur extends User {

    @CollectionTable(name = "Bricoleur_Services", joinColumns = @JoinColumn(name = "IdUser"))
    @ElementCollection(targetClass = BricolageService.class,fetch = FetchType.LAZY)
        @Enumerated(EnumType.STRING)
        private List<BricolageService> services;


    private String Description;
    private Boolean BricoleurAvailability;
    private double Rating;

    private double servicePricePerHour;
    private int totalWorkHours;
    private String mainPic;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "bricoleur", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    @JsonBackReference
    private List<Offer> offers;

}

