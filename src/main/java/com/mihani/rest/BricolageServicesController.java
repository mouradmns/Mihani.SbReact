package com.mihani.rest;


import com.mihani.entities.BricolageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@Slf4j
public class BricolageServicesController {


    @GetMapping("/services")
    public List<String> getServices(){

        List<String> services = Arrays.stream(BricolageService.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        return services;
    }
}
