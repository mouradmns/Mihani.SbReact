package com.mihani.services;

import com.mihani.entities.Announcement;
import com.mihani.entities.Comment;
import com.mihani.entities.Utilisateur;
import com.mihani.repositories.AnnouncementRepo;
import com.mihani.repositories.CommentRepo;
import com.mihani.repositories.UserRepo;
import com.mihani.util.CommentComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private AnnouncementRepo announcementRepo;

    @Autowired
    private UserRepo userRepo;

    public Comment addComment(Long idAnnouncement,Long idUser, Comment comment) throws Exception {
        Optional<Utilisateur> optionalUtilisateur = userRepo.findById(idUser);
        Optional<Announcement> optAnnouncement = announcementRepo.findById(idAnnouncement);

        if(optAnnouncement.isPresent()) {
            if(optionalUtilisateur.isPresent()) {
                Utilisateur utilisateur = optionalUtilisateur.get();
                Announcement announcement = optAnnouncement.get();
                comment.setAnnouncement(announcement);
                comment.setUser(utilisateur);
                comment = commentRepo.save(comment);

                return comment;
            }
            throw new Exception("There is no user with this id " + idAnnouncement + " to add comment for");
        }
        throw new Exception("There is no announcement with this id " + idAnnouncement + " to add comment for");

    }

    public Comment modifyComment(Long idUser, Comment comment) throws Exception {
        Optional<Comment> optionalComment = commentRepo.findById(comment.getId());

        if(optionalComment.isPresent()) {
            if(checkUser(idUser, comment.getId())) {
                return commentRepo.save(comment);
            }
            throw new Exception("The user with id " + idUser + " can not modify the comment with id " + comment.getId() +
             " because it is not his");
        }
        throw new Exception("There is no comment with id " + comment.getId() + " to update");
    }

    public List<Comment> findAllComments() {
        return commentRepo.findAll();
    }

    public List<Comment> findCommentsByAnnouncementId(Long announcementId) {
        List<Comment> comments = commentRepo.findCommentsByAnnouncementId(announcementId);
        Collections.sort(comments, new CommentComparator());
        return comments;
    }

    private boolean checkUser(Long idUser, Long idComment) {
        return commentRepo.checkUser(idUser, idComment);
    }

}
