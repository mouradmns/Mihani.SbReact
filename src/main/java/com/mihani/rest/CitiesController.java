package com.mihani.rest;

import com.mihani.entities.Cities;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CitiesController {

    @GetMapping("/cities")
    public List<String> getCities() {
        return List.of(Cities.values()).stream().map(Cities::name).toList();
    }

}
