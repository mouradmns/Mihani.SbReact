package com.mihani.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_comment")
    private LocalDate dateComment;

    @Column(name = "body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_announcement")
    @JsonBackReference(value = "announcement-comment")
    private Announcement announcement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonBackReference(value = "user-comment")
    private User user;

}
