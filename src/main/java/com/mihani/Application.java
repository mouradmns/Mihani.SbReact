package com.mihani;


import com.mihani.entities.BricolageService;
import com.mihani.entities.Bricoleur;
import com.mihani.repositories.BricoleurRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Transactional
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }





//    @Bean
//    CommandLineRunner start(BricoleurRepo bricoleurRepo) {
//        return args -> {
//            Stream.of("Ali", "AHMED", "SAId").forEach(name -> {
//                Bricoleur bricoleur = new Bricoleur();
//
//               Long  i= Long.valueOf(1222);
//                bricoleur.setIdUtilisateur(i);
//                bricoleur.setPrenom(name);
//                bricoleur.setEmail(name + "@gmail.com");
//                bricoleur.setBricoleurAvailability(true);
//                bricoleur.setDescription("im a plombier that works for every peace of money and emails ");
//
//                List<BricolageService> listSrv =new ArrayList<>();
//
//                listSrv.add(BricolageService.PLOMBERIE);
//                bricoleur.setServices(listSrv);
//
//                bricoleurRepo.save(bricoleur);
//            });
//
//        };
//    }



}
