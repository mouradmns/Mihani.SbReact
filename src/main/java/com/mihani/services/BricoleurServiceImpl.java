package com.mihani.services;

import com.mihani.dtos.BricoleurProfileDto;
import com.mihani.entities.Bricoleur;
import com.mihani.mappers.BricoleurMapperImpl;
import com.mihani.repo.BricoleurRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BricoleurServiceImpl implements BricoleurService{

     private BricoleurRepo bricoleurRepo;
    private  BricoleurMapperImpl dtoMapper;

    @Override
    public Bricoleur saveBricoleur(Bricoleur bricoleur) {
        return null;
    }

    @Override
    public Bricoleur updateBricoleur(Bricoleur bricoleur) {
        return null;
    }

    @Override
    public Bricoleur deleteBricoleur(UUID idBricoleur) {
        return null;
    }

    @Override
    public Bricoleur getBricoleur(UUID idBricoleur) {
        return null;
    }

    @Override
    public List<BricoleurProfileDto> listBricoleurs() {

        List<Bricoleur> bricoleurs = bricoleurRepo.findAll();

        log.info("bricoleurs  service--------------------------------");
        List<BricoleurProfileDto> brDtos= bricoleurs.stream().map(br->
                dtoMapper.fromBricoleur(br)).collect(Collectors.toList());

        return brDtos;
    }
}
