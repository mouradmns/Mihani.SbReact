package com.mihani.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("BR")
public class Bricoleur extends User {

    @CollectionTable(name = "Bricoleur_Services", joinColumns = @JoinColumn(name = "IdUtilisateur"))
    @Column(name = "bricoleur_services")
    @ElementCollection(targetClass = BricolageService.class,fetch = FetchType.LAZY)
        @Enumerated(EnumType.STRING)
        private List<BricolageService> services;

    private String Description;


    private Boolean BricoleurAvailability;

    private double Rating;
    private String mainPic;
    private String secondPic;
    private String thirdPic;


}
