package com.mihani.repositories;

import com.mihani.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Utilisateur, Long> {
}
