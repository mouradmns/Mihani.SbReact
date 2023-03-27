package com.mihani.repositories;

import com.mihani.entities.Announcement;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnouncementRepo extends JpaRepository<Announcement, Long>, JpaSpecificationExecutor<Announcement> {

    @Query("SELECT a FROM Announcement  a " +
            "WHERE a.title LIKE CONCAT('%', :title, '%')")
    public List<Announcement> findAnnouncementByTitle(@Param("title") String title);



    public static Specification<Announcement> titleContains(String title) {
        return new Specification<Announcement>() {
            @Override
            public Predicate toPredicate(Root<Announcement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("title"), "%" + title + "%");
            }
        };
    }

    public static Specification<Announcement> typeEquals(String type) {
        return new Specification<Announcement>() {
            @Override
            public Predicate toPredicate(Root<Announcement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("typeService"), type);
            }
        };
    }

    public static  Specification<Announcement> isAvailabale() {
        return new Specification<Announcement>() {
            @Override
            public Predicate toPredicate(Root<Announcement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("available"), true);
            }
        };
    }
}
