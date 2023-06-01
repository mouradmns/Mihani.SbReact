package com.mihani.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(name = "validated")
    private Boolean validated;

    @ElementCollection(targetClass = BricolageService.class)
    @Enumerated(EnumType.STRING)
    private List<BricolageService> typeService;

    @Enumerated(EnumType.STRING)
    private Cities city;

    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "announcement",
                cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "announcement-attachment")
    private List<AnnouncementAttachment> announcementAttachments;


    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "announcement",
                cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "announcement-comment")
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "announcement",
            cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "announcement-offer")
    private List<Offer> offers;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "announcement",
            cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "announcement-report")
    private List<Report> reports;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private User user;

    public Boolean isAvailable() {
        return this.available;
    }
}
