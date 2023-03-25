package com.mihani.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor


@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type", length = 4,discriminatorType = DiscriminatorType.STRING)


public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long IdUtilisateur ;

    private String prenom ;
    private String nom ;
    private String email ;
    private int Age ;
    private  String Tel;
    private String ville;
    private Date dateInscription  ;





}
