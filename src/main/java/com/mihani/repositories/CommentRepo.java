package com.mihani.repositories;

import com.mihani.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    public List<Comment> findCommentsByAnnouncementId(Long id);

}
