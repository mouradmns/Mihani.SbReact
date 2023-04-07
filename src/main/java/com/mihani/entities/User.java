package com.mihani.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type", length = 4,discriminatorType = DiscriminatorType.STRING)


public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdUser ;

    private String prenom ;
    private String nom ;
    private String email ;
    private int Age ;
    private  String Tel;
    private String ville;
    private Date dateInscription  ;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    @JsonManagedReference
    private List<Offer> offers;




}
