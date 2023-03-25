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

    /*public void addAnnouncementAttachment(AnnouncementAttachment attachment) {

        if(this.announcementAttachments == null) {
            this.announcementAttachments = new ArrayList<>();
        }

        this.announcementAttachments.add(attachment);
        attachment.setAnnouncement(this);
    }

    public void addComment(Comment comment) {

        if(this.comments == null) {
            this.comments = new ArrayList<>();
        }

        this.comments.add(comment);
        comment.setAnnouncement(this);
    }

    public void addOffer(Offer offer) {

        if(this.offers == null) {
            this.offers = new ArrayList<>();
        }

        this.offers.add(offer);
        offer.setAnnouncement(this);
    }

    public void addReport(Report report) {

        if(this.reports == null) {
            this.reports = new ArrayList<>();
        }

        this.reports.add(report);
        report.setAnnouncement(this);
    }*/
}
