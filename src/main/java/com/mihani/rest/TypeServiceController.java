package com.mihani.rest;

import com.mihani.entities.BricolageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('BRICOLEUR','CLIENT','ADMIN')")
public class TypeServiceController {

    @PreAuthorize("hasAnyAuthority('bricoleur:read','client:read','admin:read')")
    @GetMapping("/servicesTypes")
    public List<String> getServicesTypes() {
        return Arrays.asList(BricolageService.values()).stream().map(Enum::name).toList();
    }

}
