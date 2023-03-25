package com.mihani.web;


import com.mihani.dtos.BricoleurProfileDto;
<<<<<<< HEAD
import com.mihani.entities.Bricoleur;
import com.mihani.Exceptions.BricoleurAlreadyExistsException;
import com.mihani.Exceptions.BricoleurNotFoundException;
import com.mihani.services.BricoleurServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
=======
import com.mihani.services.BricoleurServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
>>>>>>> 5b3d7174df6847071947686fae74a7cfb05ac23a
@AllArgsConstructor
@Slf4j
public class BricoleurController {

    private BricoleurServiceImpl bricoleurService;

<<<<<<< HEAD
=======


>>>>>>> 5b3d7174df6847071947686fae74a7cfb05ac23a
    @GetMapping("/bricoleurs")
    public List<BricoleurProfileDto> bricoleurs(){
            return  bricoleurService.listBricoleurs();
    }
<<<<<<< HEAD


    @GetMapping("bricoleurs/{id}")
    public BricoleurProfileDto getBricoleur(@PathVariable Long id)
    {

        log.info("++++++++++id Utilisateur : "+id);
        return bricoleurService.getBricoleur(id);
    }


    @PostMapping("/bricoleurs")
    public ResponseEntity<Bricoleur> saveBricoleur(@RequestBody Bricoleur bricoleur) throws BricoleurAlreadyExistsException {
        Bricoleur savedBricoleur = bricoleurService.saveBricoleur(bricoleur);
        return  new ResponseEntity<>(savedBricoleur, HttpStatus.OK);
    }

    @PutMapping("bricoleurs/{id}")
    public ResponseEntity<Bricoleur> updateBricoleur(@PathVariable Long id , @RequestBody Bricoleur bricoleur) throws BricoleurNotFoundException {

        bricoleur.setIdUtilisateur(id);
        Bricoleur updatedBricoleur=bricoleurService.updateBricoleur(bricoleur);
        return new ResponseEntity<>(updatedBricoleur,HttpStatus.OK);
    }


    @DeleteMapping("bricoleurs/{id}")
    public ResponseEntity<Void> deleteBricoleur(@PathVariable Long id) throws BricoleurNotFoundException {

        bricoleurService.deleteBricoleur(id);

        return  ResponseEntity.noContent().build();

=======
    @GetMapping("/")
    public String Home(){
            return "bricoleurs";
>>>>>>> 5b3d7174df6847071947686fae74a7cfb05ac23a
    }

}
