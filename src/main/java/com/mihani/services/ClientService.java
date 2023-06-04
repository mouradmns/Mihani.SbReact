package com.mihani.services;

import com.mihani.entities.Client;
import com.mihani.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;

    public Client getClient(Long id) throws Exception {
        Optional<Client> optional =  clientRepo.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        throw new Exception("Client not found");
    }

    public Client updateClient(Client client) throws Exception {
        Optional<Client> optional =  clientRepo.findById(client.getId());
        if (optional.isPresent()){
            Client client1 = optional.get();
            return clientRepo.save(client1);
        }
        throw new Exception("Client not found");
    }

}
