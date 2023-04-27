package com.mihani;

import com.mihani.filters.CorsFilter;

import com.mihani.entities.Announcement;
import com.mihani.entities.BricolageService;
import com.mihani.entities.Bricoleur;
import com.mihani.repositories.AnnouncementRepo;
import com.mihani.repositories.BricoleurRepo;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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


    @Bean
    CommandLineRunner start(BricoleurRepo bricoleurRepo, AnnouncementRepo announcementRepo) {
        return args -> {
//            Stream.of("ALi", "Mohamed", "Ahmed").forEach(name -> {
//                Bricoleur bricoleur = new Bricoleur();
//
//
//
//                Long i = Long.valueOf(23);
//                bricoleur.setIdUser(i);
//                bricoleur.setPrenom(name);
//                bricoleur.setNom(name);
//
//                bricoleur.setRating(3.3);
//
//
//
//
//                bricoleur.setEmail(name + "@gmail.com");
//                bricoleur.setBricoleurAvailability(true);
//                bricoleur.setDescription("contains dd ff ");
//
//                int j =2;
//
//                bricoleur.setMainPic("/assets/images/bricoleurs/bric"+j+".webp");
//
//
//                List<BricolageService> listSrv = new ArrayList<>();
//
//                listSrv.add(BricolageService.ELECTRICITE);
//                bricoleur.setServices(listSrv);
//                bricoleurRepo.save(bricoleur);
//            });

//            Announcement announcement = new Announcement();
//
//            announcement.setTitle("second announcement ");
//            announcement.setAvailable(true);
//            List<BricolageService> l = new ArrayList<>();
//            l.add(BricolageService.ELECTRICITE);
//            l.add(BricolageService.AUTRE);
//            announcement.setTypeService(l);
//            announcementRepo.save(announcement);

        };
    }
}
