package com.mihani.repositories;

import com.mihani.entities.Client;
import com.mihani.entities.Comment;
import com.mihani.entities.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepoTest {

    @Autowired
    private CommentRepo commentRepo;

    @Test
    public void testCheckUser() {
        // Create a user and comment in the H2 in-memory database
        User user = new Client();
        user.setId(1L); // Set user ID
        // Set other user properties as needed

        Comment comment = new Comment();
        comment.setId(1L); // Set comment ID
        comment.setUser(user);
        // Set other comment properties as needed

        commentRepo.save(comment);

        // Test the checkUser method
        boolean userExists = commentRepo.checkUser(user.getId(), comment.getId());
        assertTrue(userExists);
    }
}