package com.mihani.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnnouncementDto {

    private Long id;
    private String title;
    private String typeService;
    private String description;
    private LocalDate appropriateDate;
    private Boolean available;


}
