package com.mihani.dtos;

import com.mihani.entities.BricolageService;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;


@Data
public class BricoleurProfileDto {

    private Long idUtilisateur;
    private String prenom;
    private String nom;

    @Enumerated(EnumType.STRING)
    private List<BricolageService> services;

    private double Rating;
    private String mainPic;

    private Boolean BricoleurAvailability;

}

