package com.mihani.repositories;

import com.mihani.entities.Announcement;
import com.mihani.entities.BricolageService;
import com.mihani.entities.Cities;
import jakarta.persistence.criteria.*;
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

    public static Specification<Announcement> typeIn(List<BricolageService> types) {
        return new Specification<Announcement>() {
            @Override
            public Predicate toPredicate(Root<Announcement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Announcement> subqueryRoot = subquery.from(Announcement.class);
                subquery.select(subqueryRoot.get("id"));

                // Join the typeService attribute of the Announcement entity with the BricolageService entity
                Join<Announcement, BricolageService> typeJoin = subqueryRoot.join("typeService");

                // Add the IN clause to the subquery
                subquery.where(typeJoin.in(types));

                return root.get("id").in(subquery);
            }
        };
    }

    public static Specification<Announcement> cityEquals(Cities city) {
        return new Specification<Announcement>() {
            @Override
            public Predicate toPredicate(Root<Announcement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("city"), city);
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
