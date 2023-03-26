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
public class ReportModel {

    private Long id;
    private LocalDate dateReport;
    private String reason;
    private String description;
    private Long idAnnouncement;

}
