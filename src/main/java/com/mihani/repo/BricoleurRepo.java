package com.mihani.repo;

import com.mihani.entities.Bricoleur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BricoleurRepo extends JpaRepository<Bricoleur, Long> {

}
