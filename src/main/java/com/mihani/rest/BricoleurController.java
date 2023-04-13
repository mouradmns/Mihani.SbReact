package com.mihani.rest;


import com.mihani.dtos.BricoleurProfileDto;


import com.mihani.entities.Bricoleur;
import com.mihani.Exceptions.BricoleurAlreadyExistsException;
import com.mihani.Exceptions.BricoleurNotFoundException;
import com.mihani.mappers.BricoleurMapperImpl;
import com.mihani.services.BricoleurServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@Slf4j
public class BricoleurController {


    @Autowired
    private BricoleurServiceImpl bricoleurService;
    @Autowired
    private BricoleurMapperImpl brciMapper;


    @GetMapping("/bricoleurs")
    public List<BricoleurProfileDto> bricoleurs(){
            return  bricoleurService.listBricoleurs();
    }



    @GetMapping("/bricoleurs/available")
    public List<BricoleurProfileDto> Filteredbricoleurs(@RequestParam(name ="service",required = false) List<String> service,
                                                        @RequestParam(name ="description",required = false) String description){
            return  bricoleurService.filteredlistOfAVailableBricoleurs(service, description);
    }


    @GetMapping("bricoleurs/{id}")
    public BricoleurProfileDto getBricoleur(@PathVariable Long id) throws BricoleurNotFoundException {

        return bricoleurService.getBricoleur(id);
    }


    @PostMapping(value = "/bricoleurs")
    public Bricoleur saveBricoleur(@RequestBody BricoleurProfileDto bricoleur) {
        log.info("+==inside Controller ");
        return  bricoleurService.saveBricoleur(brciMapper.fromBricoleurProfileDto(bricoleur));
    }

    @PutMapping(value = "bricoleurs/{id}")
    public Bricoleur updateBricoleur(@PathVariable Long id , @RequestBody BricoleurProfileDto bricoleur) throws BricoleurNotFoundException {
        bricoleur.setIdUser(id);
        return bricoleurService.updateBricoleur(brciMapper.fromBricoleurProfileDto(bricoleur));


    }


    @DeleteMapping("bricoleurs/{id}")
    public void deleteBricoleur(@PathVariable Long id) throws BricoleurNotFoundException {
        bricoleurService.deleteBricoleur(id);

    }

}
