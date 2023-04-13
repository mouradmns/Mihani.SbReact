package com.mihani.repositories;

import com.mihani.entities.Announcement;
import com.mihani.entities.BricolageService;
import com.mihani.entities.Bricoleur;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public interface BricoleurRepo extends JpaRepository<Bricoleur, Long> , JpaSpecificationExecutor<Bricoleur> {



    public static Specification<Bricoleur> hasBricolageService(String service) {
        return new Specification<Bricoleur>() {
            @Override
            public Predicate toPredicate(Root<Bricoleur> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

               List<BricolageService> servicesNames = Arrays.asList(BricolageService.values());
               Path<List<BricolageService>> services= root.get("services");
               List<Predicate> predicates= new ArrayList<>();

               for(BricolageService serviceName : servicesNames) {
                   if(serviceName.toString().equals(service.toUpperCase())){
                       predicates.add(criteriaBuilder.isMember(serviceName,services));
                   }
               }
               return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }

        };
    }


    public static Specification<Bricoleur> DescriptionContains(String description) {
        return new Specification<Bricoleur>() {
            @Override
            public Predicate toPredicate(Root<Bricoleur> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("Description"), "%" + description + "%");
            }
        };
    }


    public static  Specification<Bricoleur> isAvailable() {
        return new Specification<Bricoleur>() {
            @Override
            public Predicate toPredicate(Root<Bricoleur> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("BricoleurAvailability"), true);
            }
        };
    }




}
