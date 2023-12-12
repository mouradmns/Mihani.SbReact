package com.mihani.rest;

import com.mihani.entities.Client;
import com.mihani.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@PreAuthorize("hasAnyRole('CLIENT')")
public class ClientController {

    @Autowired  
    private ClientService clientService;

    @PreAuthorize("hasAnyAuthority('bricoleur:read','client:read','admin:read')")
    @GetMapping("/clients/{id}")
    public Client getClient(@PathVariable("id") Long id) throws Exception {
        return clientService.getClient(id);
    }

}
