package com.mihani.dtos;

import lombok.*;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentModel {

    private Long id;
    private LocalDate dateComment;
    private String body;
    private Long idAnnouncement;
    private Long idUser;

}
