package com.mihani.services;

import com.mihani.dtos.AnnouncementDto;
import com.mihani.entities.Announcement;
import com.mihani.entities.Client;
import com.mihani.entities.User;
import com.mihani.repositories.AnnouncementAttachmentRepo;
import com.mihani.repositories.AnnouncementRepo;
import com.mihani.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AnnouncementServiceTest {

    @Mock
    private AnnouncementRepo announcementRepo;

    @Mock
    private AnnouncementAttachmentRepo announcementAttachmentRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private AnnouncementService announcementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAnnouncement() throws Exception {
        AnnouncementDto dto = new AnnouncementDto();
        dto.setIdUser(1L); // Set other properties as needed
        dto.setDateAnnouncement(LocalDate.of(2022, 01, 12));
        dto.setAppropriateDate(LocalDate.of(2022, 01, 13));

        MultipartFile[] files = {
                new MockMultipartFile("file1", "file1.txt", "text/plain", "content".getBytes())
                // Add more files as needed
        };

        User user = new Client();
        user.setId(1L); // Set other user properties as needed

        when(userRepo.findById(dto.getIdUser())).thenReturn(Optional.of(user));
        when(announcementRepo.save(any(Announcement.class))).thenAnswer(invocation -> {
            Announcement announcement = invocation.getArgument(0);
            announcement.setId(1L); // Simulate the generated ID
            return announcement;
        });

        Announcement savedAnnouncement = announcementService.save(dto, files);

        assertNotNull(savedAnnouncement.getId());
        assertEquals(user, savedAnnouncement.getUser());
    }

}