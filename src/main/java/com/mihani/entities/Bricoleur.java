package com.mihani.entities;

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


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "bricoleur", cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "bricoleur-offer")
    private List<Offer> offers;

    public Bricoleur(Integer idSecurity) {
        this.setId(Long.valueOf(idSecurity));
    }
}

