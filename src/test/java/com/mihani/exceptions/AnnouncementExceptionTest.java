package com.mihani.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class AnnouncementExceptionTest {
    /**
     * Method under test:
     * {@link AnnouncementException#genericExceptionHandler(Exception)}
     */
    @Test
    void testGenericExceptionHandler() {
        AnnouncementException announcementException = new AnnouncementException();
        ResponseEntity<String> actualGenericExceptionHandlerResult = announcementException
                .genericExceptionHandler(new Exception("foo"));
        assertEquals("foo", actualGenericExceptionHandlerResult.getBody());
        assertEquals(400, actualGenericExceptionHandlerResult.getStatusCodeValue());
        assertTrue(actualGenericExceptionHandlerResult.getHeaders().isEmpty());
    }
}
