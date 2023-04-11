package com.mihani.dtos;

import com.mihani.entities.BricolageService;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;


@Data
public class BricoleurProfileDto {

    private Long idUtilisateur;
    private String prenom;
    private String nom;

    @Enumerated(EnumType.STRING)
    private BricolageService service;

    private double Rating;
    private String mainPic;


}

