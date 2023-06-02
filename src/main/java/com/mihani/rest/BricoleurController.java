package com.mihani.rest;


import com.mihani.dtos.BricoleurProfileDto;


import com.mihani.entities.Bricoleur;
import com.mihani.exceptions.BricoleurAlreadyExistsException;
import com.mihani.exceptions.BricoleurNotFoundException;
import com.mihani.mappers.BricoleurMapperImpl;

import com.mihani.services.BricoleurServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@Slf4j
@PreAuthorize("hasRole('BRICOLEUR')")
public class BricoleurController {

    @Autowired
    private BricoleurServiceImpl bricoleurService;
    @Autowired
    private BricoleurMapperImpl brciMapper;

    @GetMapping("/bricoleurs")
    @PreAuthorize("hasAuthority('bricoleur:read')")
    public List<BricoleurProfileDto> bricoleurs(){
            return  bricoleurService.listBricoleurs();
    }

    @GetMapping("/bricoleurs/available")
    @PreAuthorize("hasAuthority('bricoleur:read')")
    public List<BricoleurProfileDto> Filteredbricoleurs(@RequestParam(name ="service",required = false) List<String> service,
                                                        @RequestParam(name ="description",required = false) String description){
            return  bricoleurService.filteredlistOfAVailableBricoleurs(service, description);
    }


    @GetMapping("bricoleurs/{id}")
    @PreAuthorize("hasAuthority('bricoleur:read')")
    public BricoleurProfileDto getBricoleur(@PathVariable Long id) throws BricoleurNotFoundException {
        return bricoleurService.getBricoleur(id);
    }


    @PostMapping(value = "/bricoleurs")
    @PreAuthorize("hasAuthority('bricoleur:create')")
    public Bricoleur saveBricoleur(@RequestBody BricoleurProfileDto bricoleur) throws BricoleurAlreadyExistsException {
        return  bricoleurService.saveBricoleur(brciMapper.fromBricoleurProfileDto(bricoleur));
    }

        @PutMapping(value = "bricoleurs/{id}")
        @PreAuthorize("hasAuthority('bricoleur:update')")
        public ResponseEntity<Bricoleur> updateBricoleur(@PathVariable Long id , @RequestBody BricoleurProfileDto bricoleur) throws BricoleurNotFoundException {

            bricoleur.setId(id);
            Bricoleur updatedBricoleur=bricoleurService.updateBricoleur(brciMapper.fromBricoleurProfileDto(bricoleur));
            return new ResponseEntity<>(updatedBricoleur, HttpStatus.OK);
        }

        @DeleteMapping("bricoleurs/{id}")
        @PreAuthorize("hasAuthority('bricoleur:delete')")
        public void deleteBricoleur(@PathVariable Long id) throws BricoleurNotFoundException {
            bricoleurService.deleteBricoleur(id);

        }

}
