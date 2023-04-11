package com.mihani;

import com.mihani.filters.CorsFilter;

import com.mihani.entities.BricolageService;
import com.mihani.entities.Bricoleur;
import com.mihani.repositories.BricoleurRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Transactional
@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registrationBean =
                new FilterRegistrationBean(new CorsFilter());
        registrationBean.setName("CORS filter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
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
