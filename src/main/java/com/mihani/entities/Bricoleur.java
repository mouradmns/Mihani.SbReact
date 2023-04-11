package com.mihani.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("BR")
public class Bricoleur extends User {

    @Enumerated(EnumType.STRING)
    private BricolageService service;

    private String Description;
    private double Rating;
    private String mainPic;
    private String secondPic;
    private String thirdPic;

}
