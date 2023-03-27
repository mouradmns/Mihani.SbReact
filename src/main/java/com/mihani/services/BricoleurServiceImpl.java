package com.mihani.services;

import com.mihani.exceptions.BricoleurAlreadyExistsException;
import com.mihani.exceptions.BricoleurNotFoundException;
import com.mihani.dtos.BricoleurProfileDto;
import com.mihani.entities.Bricoleur;


import com.mihani.mappers.BricoleurMapperImpl;
import com.mihani.repositories.BricoleurRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BricoleurServiceImpl implements BricoleurService{

         private BricoleurRepo bricoleurRepo;
    private  BricoleurMapperImpl dtoMapper;

    @Override

    public Bricoleur saveBricoleur(Bricoleur bricoleur) throws BricoleurAlreadyExistsException {
        Optional<Bricoleur> existingBricoleur = bricoleurRepo.findById(bricoleur.getIdUtilisateur());
        if(existingBricoleur.isPresent()){
            throw  new BricoleurAlreadyExistsException("a Bricoleur with the same id already exists!!");
        }
        return bricoleurRepo.save(bricoleur);

    }

    @Override
    public Bricoleur updateBricoleur(Bricoleur bricoleur) throws BricoleurNotFoundException {
        Optional<Bricoleur> existingBricoleur = bricoleurRepo.findById(bricoleur.getIdUtilisateur());

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

    @Override
    public BricoleurProfileDto getBricoleur(Long idBricoleur) {
        return dtoMapper.fromBricoleur(bricoleurRepo.getReferenceById(idBricoleur)  );
    }
    @Override
    public List<BricoleurProfileDto> listBricoleurs() {

        List<Bricoleur> bricoleurs = bricoleurRepo.findAll();


        log.info(" ----------------------------bricoleurs  service Mapper--------------------------------");

        List<BricoleurProfileDto> brDto= bricoleurs.stream().map(br->
                dtoMapper.fromBricoleur(br)).toList();

        return brDto;

    }

    @Override
    public List<BricoleurProfileDto> filteredlistOfAVailableBricoleurs(String service, String description) {

        Specification<Bricoleur> filteredSepc =Specification.where(BricoleurRepo.isAvailable());
        Specification<Bricoleur> serviceSpec=null;
        Specification<Bricoleur> descriptionSpec=null;

        if(service != null)
            serviceSpec = BricoleurRepo.hasBricolageService(service);
        if(description != null)
           descriptionSpec = BricoleurRepo.DescriptionContains(description);

        if(descriptionSpec != null && serviceSpec != null)
            filteredSepc = filteredSepc.and(descriptionSpec).and(serviceSpec);
        else if(serviceSpec != null)
            filteredSepc=filteredSepc.and(serviceSpec);
        else if (descriptionSpec != null)
                filteredSepc=filteredSepc.and(descriptionSpec);


        List<Bricoleur> bricoleurs = bricoleurRepo.findAll(filteredSepc);

        List<BricoleurProfileDto> brDto= bricoleurs.stream().map(br->
                dtoMapper.fromBricoleur(br)).toList();

        return brDto;
    }
}
