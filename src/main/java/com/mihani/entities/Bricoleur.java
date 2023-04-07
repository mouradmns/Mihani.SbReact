package com.mihani.entities;


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


    private Boolean BricoleurAvailability;

    private String Description;

    private double Rating;

    private double servicePricePerHour;
    private int totalWorkHours;

    private String mainPic;
    private String secondPic;
    private String thirdPic;


    @CollectionTable(name = "Bricoleur_Services", joinColumns = @JoinColumn(name = "IdUtilisateur"))
    @Column(name = "bricoleur_services")
    @ElementCollection(targetClass = BricolageService.class,fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private List<BricolageService> services;


}

