package com.mihani.mappers;

import com.mihani.dtos.CommentModel;
import com.mihani.entities.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentModel toCommentModel(Comment comment) {
        CommentModel commentModel = CommentModel.builder()
                .id(comment.getId())
                .dateComment(comment.getDateComment())
                .body(comment.getBody())
                .idUser(comment.getUser().getId())
                .idAnnouncement(comment.getAnnouncement().getId())
                .username(comment.getUser().getNom() + " " + comment.getUser().getPrenom())
                .userProfileImage(comment.getUser().getMainPic())
                .build();
        return commentModel;
    }

}
