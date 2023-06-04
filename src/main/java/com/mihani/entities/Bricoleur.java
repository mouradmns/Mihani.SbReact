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

    @CollectionTable(name = "Bricoleur_Services", joinColumns = @JoinColumn(name = "id_user"))
    @ElementCollection(targetClass = BricolageService.class,fetch = FetchType.LAZY)
        @Enumerated(EnumType.STRING)
        private List<BricolageService> services;


    private String Description;
    private Boolean BricoleurAvailability;
    private Double Rating;
    private Double servicePricePerHour;
    private Integer totalWorkHours;
    private String mainPic;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "bricoleur", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    @JsonBackReference
    private List<Offer> offers;

    public Bricoleur(Integer idSecurity) {
        this.setId(Long.valueOf(idSecurity));
    }
}

