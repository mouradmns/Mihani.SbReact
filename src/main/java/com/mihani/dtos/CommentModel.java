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
public class CommentModel {

    private Long id;
    private LocalDate dateComment;
    private String body;
    private Long idAnnouncement;

}
