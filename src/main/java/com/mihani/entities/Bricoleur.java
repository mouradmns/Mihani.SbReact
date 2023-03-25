package com.mihani.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

//@DiscriminatorValue("BR")
public class Bricoleur extends Utilisateur {



    @Enumerated(EnumType.STRING)
    private ServicesBricolage service;

    private String Description;

    private double Rating;

       private String mainPic;
    private String secondPic;
    private String thirdPic;

}
