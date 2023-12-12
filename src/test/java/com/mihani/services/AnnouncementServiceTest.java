package com.mihani.services;

import com.mihani.config.BackendURL;
import com.mihani.dtos.AnnouncementDto;
import com.mihani.entities.*;
import com.mihani.repositories.AnnouncementAttachmentRepo;
import com.mihani.repositories.AnnouncementRepo;
import com.mihani.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnnouncementServiceTest {

    @Mock private AnnouncementRepo announcementRepo;
    @Mock private AnnouncementAttachmentRepo announcementAttachmentRepo;
    @Mock private UserRepo userRepo;
    @Mock private BackendURL backendURL;
    @InjectMocks private AnnouncementService announcementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAnnouncement_normalExecution() throws Exception {
        AnnouncementDto dto = new AnnouncementDto();
        dto.setIdUser(1L); // Set other properties as needed
        dto.setDateAnnouncement(LocalDate.of(2022, 01, 12));
        dto.setAppropriateDate(LocalDate.of(2022, 01, 13));

        MultipartFile[] files = {
                new MockMultipartFile("file1", "file1.txt", "text/plain", "content".getBytes())
        };

        User user = new Client();
        user.setId(1L); // Set other user properties as needed

        when(userRepo.findById(dto.getIdUser())).thenReturn(Optional.of(user));
        when(announcementRepo.save(any(Announcement.class))).thenAnswer(invocation -> {
            Announcement announcement = invocation.getArgument(0);
            announcement.setId(1L); // Simulate the generated ID
            return announcement;
        });
        when(backendURL.getBackendURL()).thenReturn("http://localhost:8085");

        Announcement savedAnnouncement = announcementService.save(dto, files);

        assertNotNull(savedAnnouncement.getId());
        assertEquals(user, savedAnnouncement.getUser());
    }

    @Test
    void update_ExistingAnnouncement_ReturnsUpdatedAnnouncementDto() throws Exception {
        // Arrange
        AnnouncementDto dto = new AnnouncementDto();
        dto.setId(1L);
        dto.setIdUser(2L);
        dto.setDescription("Updated Description");

        MultipartFile[] files = null;

        User user = new Client();
        user.setId(2L);

        Announcement existingAnnouncement = new Announcement();
        existingAnnouncement.setId(1L);
        existingAnnouncement.setUser(user);
        existingAnnouncement.setAvailable(true);

        when(announcementRepo.findById(anyLong())).thenReturn(Optional.of(existingAnnouncement));
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(announcementRepo.save(any(Announcement.class))).thenReturn(existingAnnouncement);

        // Act
        AnnouncementDto updatedDto = announcementService.update(dto, files);

        // Assert
        assertNotNull(updatedDto);
        assertEquals(1L, updatedDto.getId());
        assertEquals("Updated Description", updatedDto.getDescription());
        verify(announcementRepo, times(1)).findById(anyLong());
        verify(userRepo, times(1)).findById(anyLong());
        verify(announcementRepo, times(1)).save(any(Announcement.class));
        verify(announcementAttachmentRepo, times(0)).save(any(AnnouncementAttachment.class));
    }

    @Test
    void deleteById_ExistingAnnouncement_DeletesAnnouncement() throws Exception {
        // Arrange
        Announcement existingAnnouncement = new Announcement();
        existingAnnouncement.setId(1L);
        when(announcementRepo.findById(anyLong())).thenReturn(Optional.of(existingAnnouncement));

        // Act
        announcementService.deleteById(1L, 2L);

        // Assert
        verify(announcementRepo, times(1)).findById(anyLong());
        verify(announcementRepo, times(1)).deleteById(anyLong());
    }

    @Test
    void findById_ExistingAnnouncement_ReturnsAnnouncementDto() throws Exception {
        // Arrange
        Announcement existingAnnouncement = new Announcement();
        existingAnnouncement.setId(1L);
        User user = new Client();
        user.setId(1L);
        existingAnnouncement.setUser(user);
        when(announcementRepo.findById(anyLong())).thenReturn(Optional.of(existingAnnouncement));

        // Act
        AnnouncementDto announcementDto = announcementService.findById(1L);

        // Assert
        assertNotNull(announcementDto);
        assertEquals(1L, announcementDto.getId());
        verify(announcementRepo, times(1)).findById(anyLong());
    }

    @Test
    void findAvailableAnnouncementByFilter_ReturnsAnnouncementDtoList() {
        // Arrange
        String title = "Test";
        List<BricolageService> types = new ArrayList<>();
        Cities city = Cities.FES;

        when(announcementRepo.findAll(any(Specification.class))).thenReturn(new ArrayList<>());

        // Act
        List<AnnouncementDto> result = announcementService.findAvailableAnnouncementByFilter(title, types, city);

        // Assert
        assertNotNull(result);
        verify(announcementRepo, times(1)).findAll(any(Specification.class));
    }

}