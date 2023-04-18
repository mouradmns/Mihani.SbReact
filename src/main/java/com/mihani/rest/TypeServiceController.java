package com.mihani.rest;

import com.mihani.entities.BricolageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TypeServiceController {

    @GetMapping("/servicesTypes")
    public List<String> getServicesTypes() {
        return Arrays.asList(BricolageService.values()).stream().map(Enum::name).toList();
    }

}
