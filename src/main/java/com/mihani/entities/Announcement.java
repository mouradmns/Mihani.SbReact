package com.mihani.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "announcement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type_service")
    private String typeService;

    @Column(name = "description")
    private String description;

    @Column(name = "date_announcement")
    private LocalDate dateAnnouncement;

    @Column(name = "appropriate_date")
    private LocalDate appropriateDate;

    @Column(name = "available")
    private Boolean available;

    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "announcement")
    @JsonManagedReference
    private List<AnnouncementAttachment> announcementAttachments;


    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "announcement")
    @JsonManagedReference
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "announcement")
    @JsonManagedReference
    private List<Offer> offers;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "announcement")
    @JsonManagedReference
    private List<Report> reports;

    public Boolean isAvailable() {
        return this.available;
    }

}
