package com.mihani.services;

import com.mihani.entities.Admin;
import com.mihani.repositories.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    public Admin getAdmin(Long id) throws Exception {
        return adminRepo.findById(id).orElseThrow(()->new Exception("Admin not found"));
    }

}
