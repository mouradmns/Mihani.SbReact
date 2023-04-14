package com.mihani.services;

import com.mihani.exceptions.BricoleurNotFoundException;
import com.mihani.dtos.BricoleurProfileDto;
import com.mihani.entities.Bricoleur;


import com.mihani.mappers.BricoleurMapperImpl;
import com.mihani.repositories.BricoleurRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BricoleurServiceImpl implements BricoleurService{

    @Autowired
    private BricoleurRepo bricoleurRepo;
    @Autowired
    private  BricoleurMapperImpl dtoMapper;


    @Override
    public List<BricoleurProfileDto> listBricoleurs() {
    
        List<Bricoleur> bricoleurs = bricoleurRepo.findAll();

        List<BricoleurProfileDto> brDto= bricoleurs.stream().map(br->
                dtoMapper.fromBricoleur(br)).toList();
        return brDto;
    }

    @Override
    public List<BricoleurProfileDto> filteredlistOfAVailableBricoleurs(List<String> services, String description) {

        Specification<Bricoleur> filteredSepc =Specification.where(BricoleurRepo.isAvailable());
        Specification<Bricoleur> combinedSpec=null;
        Specification<Bricoleur> descriptionSpec=null;

        List<Specification<Bricoleur>> specifications = new ArrayList<>();


        if(!services.isEmpty())
            for (String service : services) {
                specifications.add(BricoleurRepo.hasBricolageService(service));
            }
        combinedSpec = specifications.stream()
                .reduce(Specification<Bricoleur>::or)
                .orElse(null);


        if(description != null)
            descriptionSpec = BricoleurRepo.DescriptionContains(description);
        if(descriptionSpec != null && combinedSpec != null)
            filteredSepc = filteredSepc.and(descriptionSpec).and(combinedSpec);
        else if(combinedSpec != null)
            filteredSepc=filteredSepc.and(combinedSpec);
        else if (descriptionSpec != null)
            filteredSepc=filteredSepc.and(descriptionSpec);


        List<Bricoleur> bricoleurs = bricoleurRepo.findAll(filteredSepc);

        List<BricoleurProfileDto> brDto= bricoleurs.stream().map(br->
                dtoMapper.fromBricoleur(br)).toList();
        return brDto;
    }


    @Override
    public BricoleurProfileDto getBricoleur(Long idBricoleur) throws BricoleurNotFoundException {

        Optional<Bricoleur> bricById= bricoleurRepo.findById(idBricoleur);
        if(bricById.isPresent()) {
            return dtoMapper.fromBricoleur(bricoleurRepo.getReferenceById(idBricoleur));
        }
        throw new BricoleurNotFoundException(" this id does not refer to any bricoleur");
    }


    @Override
    public Bricoleur saveBricoleur(Bricoleur bricoleur) throws BricoleurAlreadyExistsException {
        Optional<Bricoleur> existingBricoleur = bricoleurRepo.findById(bricoleur.getId());
        if(existingBricoleur.isPresent()){
            throw  new BricoleurAlreadyExistsException("a Bricoleur with the same id already exists!!");
        }
        return bricoleurRepo.save(bricoleur);
    }

    @Override
    public Bricoleur updateBricoleur(Bricoleur bricoleur) throws BricoleurNotFoundException {
        Optional<Bricoleur> existingBricoleur = bricoleurRepo.findById(bricoleur.getIdUser());
        if(existingBricoleur.isPresent()){
            return bricoleurRepo.save(bricoleur);
        }else {
            throw new BricoleurNotFoundException("IdBricoleur given do not match Database !!");
        }

    }

    @Override
    public void deleteBricoleur(Long idBricoleur) throws BricoleurNotFoundException {
        Optional<Bricoleur> existingBricoleur = bricoleurRepo.findById(idBricoleur);

        if(existingBricoleur.isPresent()) {
             bricoleurRepo.deleteById(idBricoleur);
        }else {
            throw new BricoleurNotFoundException("Bricoleur to delete  not found");
        }
    }

}
