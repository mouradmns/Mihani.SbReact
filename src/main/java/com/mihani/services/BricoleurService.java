package com.mihani.services;

import com.mihani.dtos.BricoleurProfileDto;
import com.mihani.entities.Bricoleur;

import java.util.List;
import java.util.UUID;

public interface BricoleurService {

    Bricoleur saveBricoleur(Bricoleur bricoleur);
    Bricoleur updateBricoleur(Bricoleur bricoleur);
    Bricoleur deleteBricoleur(UUID idBricoleur);

    Bricoleur  getBricoleur(UUID idBricoleur);

    List<BricoleurProfileDto> listBricoleurs();



}
