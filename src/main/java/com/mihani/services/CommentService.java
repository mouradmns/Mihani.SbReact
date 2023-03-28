package com.mihani.services;

import com.mihani.entities.Announcement;
import com.mihani.entities.Comment;
import com.mihani.entities.User;
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
        Optional<User> optionalUtilisateur = userRepo.findById(idUser);
        Optional<Announcement> optAnnouncement = announcementRepo.findById(idAnnouncement);

        if(optAnnouncement.isPresent()) {
            if(optionalUtilisateur.isPresent()) {
                User user = optionalUtilisateur.get();
                Announcement announcement = optAnnouncement.get();
                comment.setAnnouncement(announcement);
                comment.setUser(user);
                comment = commentRepo.save(comment);

                return comment;
            }
            throw new Exception("There is no user with this id " + idAnnouncement + " to add comment for");
        }
        throw new Exception("There is no announcement with this id " + idAnnouncement + " to add comment for");

    }

    public Comment modifyComment(Long idUser, Long idAnnouncement, Comment comment) throws Exception {
        Optional<Comment> optionalComment = commentRepo.findById(comment.getId());
        Optional<User> optionalUser = userRepo.findById(idUser);
        Optional<Announcement> optionalAnnouncement = announcementRepo.findById(idAnnouncement);

        if(optionalComment.isPresent()) {
            if(optionalUser.isPresent() && optionalAnnouncement.isPresent()) {
                comment.setUser(optionalUser.get());
                comment.setAnnouncement(optionalAnnouncement.get());
                if (checkUser(idUser, comment.getId())) {
                    return commentRepo.save(comment);
                }
                throw new Exception("The user with id " + idUser + " can not modify the comment with id " + comment.getId() +
                        " because it is not his own");
            }
            throw new Exception("The user with id: " + idUser + " or announcement with id: " + idAnnouncement +
                    " or both don't exist in database");
        }
        throw new Exception("There is no comment with id " + comment.getId() + " to update");
    }

    public void deleteComment(Long idUser, Long idComment) throws Exception {
        Optional<Comment> optionalComment = commentRepo.findById(idComment);
        Optional<User> optionalUser = userRepo.findById(idUser);

        if(optionalComment.isPresent()) {
            if(optionalUser.isPresent()) {
                if (checkUser(idUser, idComment)) {
                    commentRepo.deleteById(idComment);
                    return;
                }
                throw new Exception("The user with id " + idUser + " can not delete the comment with id " + idComment +
                        " because it is not his own");
            }
            throw new Exception("The user with id: " + idUser + " don't exist in database");
        }
        throw new Exception("There is no comment with id " + idComment + " to delete");

    }

    public List<Comment> findCommentsByAnnouncementId(Long announcementId) {
        List<Comment> comments = commentRepo.findCommentsByAnnouncementId(announcementId);
        Collections.sort(comments, new CommentComparator());
        return comments;
    }

    public List<Comment> findCommentsByUserId(Long userId) {
        List<Comment> comments = commentRepo.findCommentsByUserId(userId);
        Collections.sort(comments, new CommentComparator());
        return comments;
    }

    private boolean checkUser(Long idUser, Long idComment) {
        return commentRepo.checkUser(idUser, idComment);
    }

}
