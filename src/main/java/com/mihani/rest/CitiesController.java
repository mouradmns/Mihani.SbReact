package com.mihani.rest;

import com.mihani.entities.Cities;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('BRICOLEUR','CLIENT','ADMIN')")
public class CitiesController {


    @PreAuthorize("hasAnyAuthority('bricoleur:read','client:read','admin:read')")
    @GetMapping("/cities")
    public List<String> getCities() {
        return List.of(Cities.values()).stream().map(Cities::name).toList();
    }

}
