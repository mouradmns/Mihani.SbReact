package com.mihani.services;

import com.mihani.entities.Announcement;
import com.mihani.entities.Comment;
import com.mihani.repositories.AnnouncementRepo;
import com.mihani.repositories.CommentRepo;
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

    public Comment addComment(Long idAnnouncement, Comment comment) throws Exception {
        Optional<Announcement> test = announcementRepo.findById(idAnnouncement);

        if(test.isPresent()) {
            Announcement announcement = test.get();
            comment.setAnnouncement(announcement);
            comment = commentRepo.save(comment);

            return comment;
        }
        throw new Exception("There is no announcement with this id " + idAnnouncement + " to add comment for");

    }

    public Comment modifyComment(Comment comment) throws Exception {
        Optional<Comment> optionalComment = commentRepo.findById(comment.getId());

        if(optionalComment.isPresent()) {
            return commentRepo.save(comment);
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

}
