package com.mihani.dtos;

import com.mihani.entities.ServicesBricolage;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.UUID;

@Data
public class BricoleurProfileDto {

    private Long idUtilisateur;
    private String prenom;
    private String nom;

    @Enumerated(EnumType.STRING)
    private ServicesBricolage service;

    private double Rating;

    private String mainPic;


}
