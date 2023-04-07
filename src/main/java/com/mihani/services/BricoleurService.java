package com.mihani.services;

import com.mihani.Exceptions.BricoleurAlreadyExistsException;
import com.mihani.Exceptions.BricoleurNotFoundException;
import com.mihani.dtos.BricoleurProfileDto;
import com.mihani.entities.Bricoleur;

import java.util.List;


public interface BricoleurService {

    Bricoleur saveBricoleur(Bricoleur bricoleur) throws BricoleurAlreadyExistsException;
    Bricoleur updateBricoleur(Bricoleur bricoleur) throws BricoleurNotFoundException, BricoleurNotFoundException;
    void deleteBricoleur(Long idBricoleur) throws BricoleurNotFoundException;

    BricoleurProfileDto getBricoleur(Long idBricoleur) throws BricoleurNotFoundException;

    List<BricoleurProfileDto> listBricoleurs();

    List<BricoleurProfileDto> filteredlistOfAVailableBricoleurs(List<String> service, String description);





}
