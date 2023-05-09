package com.mihani.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type", length = 4,discriminatorType = DiscriminatorType.STRING)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private Integer age;
    private  String tel;
    private String ville;
    private Date dateInscription ;
    private String mainPic;
    private Boolean isAvailable;

    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "user",
                cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "user-comment")
    private List<Comment> comments;

}
