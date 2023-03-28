package com.mihani.repositories;

import com.mihani.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    public List<Comment> findCommentsByAnnouncementId(Long idAnnouncement);
    public List<Comment> findCommentsByUserId(Long idUser);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Comment c " +
            "WHERE c.user.id = :idUser " +
            "AND c.id = :idComment")
    public boolean checkUser(Long idUser, Long idComment);


}
