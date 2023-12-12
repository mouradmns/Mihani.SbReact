package com.mihani.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.mihani.entities.Comment;
import com.mihani.dtos.CommentModel;
import com.mihani.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void testFindAllComments() throws Exception {
        // Mocking data
        List<Comment> comments = new ArrayList<>();

        when(commentService.findAllComments()).thenReturn(comments);

        // Test
        mockMvc.perform(MockMvcRequestBuilders.get("/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        // Verification
        verify(commentService, times(1)).findAllComments();
    }

    @Test
    void testSaveComment() throws Exception {
        // Mocking data
        CommentModel commentModel = new CommentModel();
        commentModel.setIdAnnouncement(1L);
        commentModel.setIdUser(2L);
        commentModel.setBody("Test Comment");
        commentModel.setDateComment(LocalDate.now());

        when(commentService.addComment(eq(commentModel.getIdAnnouncement()), eq(commentModel.getIdUser()), any(Comment.class))).thenReturn(new CommentModel());

        // Test
        mockMvc.perform(MockMvcRequestBuilders.post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(commentModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isMap());

        // Verification
        verify(commentService, times(1)).addComment(eq(commentModel.getIdAnnouncement()), eq(commentModel.getIdUser()), any(Comment.class));
    }

    @Test
    void testModifyComment() throws Exception {
        // Mocking data
        CommentModel commentModel = new CommentModel();
        commentModel.setId(1L);
        commentModel.setIdAnnouncement(2L);
        commentModel.setIdUser(3L);
        commentModel.setBody("Updated Comment");
        commentModel.setDateComment(LocalDate.now());

        when(commentService.modifyComment(eq(commentModel.getIdUser()), eq(commentModel.getIdAnnouncement()), any(Comment.class))).thenReturn(new CommentModel());

        // Test
        mockMvc.perform(MockMvcRequestBuilders.put("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(commentModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isMap());

        // Verification
        verify(commentService, times(1)).modifyComment(eq(commentModel.getIdUser()), eq(commentModel.getIdAnnouncement()), any(Comment.class));
    }

    @Test
    void testDeleteComment() throws Exception {
        // Mocking data
        CommentModel commentModel = new CommentModel();
        commentModel.setId(1L);
        commentModel.setIdUser(2L);

        doNothing().when(commentService).deleteComment(eq(commentModel.getIdUser()), eq(commentModel.getId()));

        // Test
        mockMvc.perform(MockMvcRequestBuilders.delete("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(commentModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verification
        verify(commentService, times(1)).deleteComment(eq(commentModel.getIdUser()), eq(commentModel.getId()));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper()
                    .registerModule(new JSR310Module())
                    .writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
