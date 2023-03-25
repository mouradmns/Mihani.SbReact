package com.mihani;


import com.mihani.entities.Bricoleur;
import com.mihani.repositories.BricoleurRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }





    @Bean
    CommandLineRunner start(BricoleurRepo bricoleurRepo) {
        return args -> {
            Stream.of("Hassan", "Yassine", "Aicha").forEach(name -> {
                Bricoleur bricoleur = new Bricoleur();

               Long  i= Long.valueOf(1222);
                bricoleur.setIdUtilisateur(i);
                bricoleur.setPrenom(name);
                bricoleur.setEmail(name + "@gmail.com");
                bricoleurRepo.save(bricoleur);
            });

        };
    }



}
