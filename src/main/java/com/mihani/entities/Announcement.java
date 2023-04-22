package com.mihani.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "announcement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;


    @Column(name = "description")
    private String description;

    @Column(name = "date_announcement")
    private LocalDate dateAnnouncement;

    @Column(name = "appropriate_date")
    private LocalDate appropriateDate;

    @Column(name = "available")
    private Boolean available;

    @ElementCollection(targetClass = BricolageService.class)
    @Enumerated(EnumType.STRING)
    private List<BricolageService> typeService;

    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "announcement",
                cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<AnnouncementAttachment> announcementAttachments;


    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "announcement",
                cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "announcement",
            cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Offer> offers;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "announcement",
            cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Report> reports;

    public Boolean isAvailable() {
        return this.available;
    }
}
