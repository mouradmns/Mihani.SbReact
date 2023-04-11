package com.mihani.mappers;

import com.mihani.dtos.BricoleurProfileDto;
import com.mihani.entities.Bricoleur;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class
BricoleurMapperImpl {

    public BricoleurProfileDto fromBricoleur(Bricoleur bricoleur){
        BricoleurProfileDto brDto= new BricoleurProfileDto();

        BeanUtils.copyProperties(bricoleur,brDto);

        return brDto;
    }
    public Bricoleur fromBricoleurProfileDto(BricoleurProfileDto brDto){

            Bricoleur bricoleur= new Bricoleur();
        BeanUtils.copyProperties(brDto,bricoleur);
        return bricoleur;
    }
}
