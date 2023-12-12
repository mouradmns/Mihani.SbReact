package com.mihani.services;

import com.mihani.dtos.CommentModel;
import com.mihani.entities.Announcement;
import com.mihani.entities.Client;
import com.mihani.entities.Comment;
import com.mihani.entities.User;
import com.mihani.mappers.CommentMapper;
import com.mihani.repositories.AnnouncementRepo;
import com.mihani.repositories.CommentRepo;
import com.mihani.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepo commentRepo;

    @Mock
    private AnnouncementRepo announcementRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllComments() {
        // Mocking data
        List<Comment> comments = new ArrayList<>();
        when(commentRepo.findAll()).thenReturn(comments);

        // Test
        List<Comment> result = commentService.findAllComments();

        // Verification
        verify(commentRepo, times(1)).findAll();
        assertEquals(comments, result);
    }

    @Test
    void testAddComment() throws Exception {
        // Mocking data
        Long idAnnouncement = 1L;
        Long idUser = 2L;
        Comment comment = new Comment();
        User user = new Client();
        Announcement announcement = new Announcement();
        CommentModel commentModel = new CommentModel();

        when(userRepo.findById(idUser)).thenReturn(Optional.of(user));
        when(announcementRepo.findById(idAnnouncement)).thenReturn(Optional.of(announcement));
        when(commentRepo.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.toCommentModel(comment)).thenReturn(commentModel);

        // Test
        CommentModel result = commentService.addComment(idAnnouncement, idUser, comment);

        // Verification
        verify(userRepo, times(1)).findById(idUser);
        verify(announcementRepo, times(1)).findById(idAnnouncement);
        verify(commentRepo, times(1)).save(comment);
        verify(commentMapper, times(1)).toCommentModel(comment);

        assertEquals(commentModel, result);
    }

    @Test
    void testModifyComment() throws Exception {
        // Mocking data
        Long idUser = 2L;
        Long idAnnouncement = 3L;
        Comment comment = new Comment();
        Comment existingComment = new Comment();
        User user = new Client();
        Announcement announcement = new Announcement();
        CommentModel commentModel = new CommentModel();

        when(commentRepo.findById(comment.getId())).thenReturn(Optional.of(existingComment));
        when(userRepo.findById(idUser)).thenReturn(Optional.of(user));
        when(announcementRepo.findById(idAnnouncement)).thenReturn(Optional.of(announcement));
        when(commentRepo.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.toCommentModel(comment)).thenReturn(commentModel);
        when(commentRepo.checkUser(idUser, comment.getId())).thenReturn(true);

        // Test
        CommentModel result = commentService.modifyComment(idUser, idAnnouncement, comment);

        // Verification
        verify(commentRepo, times(1)).findById(comment.getId());
        verify(userRepo, times(1)).findById(idUser);
        verify(announcementRepo, times(1)).findById(idAnnouncement);
        verify(commentRepo, times(1)).save(comment);
        verify(commentMapper, times(1)).toCommentModel(comment);
        verify(commentRepo, times(1)).checkUser(idUser, comment.getId());

        assertEquals(commentModel, result);
    }

    @Test
    void testDeleteComment() throws Exception {
        // Mocking data
        Long idUser = 2L;
        Long idComment = 3L;
        Comment comment = new Comment();
        User user = new Client();

        when(commentRepo.findById(idComment)).thenReturn(Optional.of(comment));
        when(userRepo.findById(idUser)).thenReturn(Optional.of(user));
        when(commentRepo.checkUser(idUser, idComment)).thenReturn(true);

        // Test
        assertDoesNotThrow(() -> commentService.deleteComment(idUser, idComment));

        // Verification
        verify(commentRepo, times(1)).findById(idComment);
        verify(userRepo, times(1)).findById(idUser);
        verify(commentRepo, times(1)).checkUser(idUser, idComment);
        verify(commentRepo, times(1)).deleteById(idComment);
    }

    @Test
    void testFindCommentsByAnnouncementId() {
        // Mocking data
        Long announcementId = 1L;
        List<Comment> comments = new ArrayList<>();
        when(commentRepo.findCommentsByAnnouncementId(announcementId)).thenReturn(comments);

        // Test
        List<CommentModel> result = commentService.findCommentsByAnnouncementId(announcementId);

        // Verification
        verify(commentRepo, times(1)).findCommentsByAnnouncementId(announcementId);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindCommentsByUserId() {
        // Mocking data
        Long userId = 1L;
        List<Comment> comments = new ArrayList<>();
        when(commentRepo.findCommentsByUserId(userId)).thenReturn(comments);

        // Test
        List<Comment> result = commentService.findCommentsByUserId(userId);

        // Verification
        verify(commentRepo, times(1)).findCommentsByUserId(userId);
        assertTrue(result.isEmpty());
    }
}