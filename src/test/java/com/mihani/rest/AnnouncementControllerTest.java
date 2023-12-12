package com.mihani.rest;

import com.mihani.dtos.AnnouncementDto;
import com.mihani.dtos.CommentModel;
import com.mihani.entities.Announcement;
import com.mihani.entities.BricolageService;
import com.mihani.entities.Cities;
import com.mihani.services.AnnouncementService;
import com.mihani.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnnouncementControllerTest {

    @Mock
    private AnnouncementService announcementService;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private AnnouncementController underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByFilter() {
        // Mocking data
        String title = "Test Title";
        String[] type = {"PLOMBERIE", "ELECTRICITE"};
        String city = "FES";
        List<AnnouncementDto> announcementDtos = new ArrayList<>();

        when(announcementService.findAvailableAnnouncementByFilter(title, Arrays.asList(BricolageService.PLOMBERIE, BricolageService.ELECTRICITE), Cities.FES)).thenReturn(announcementDtos);

        // Test
        List<AnnouncementDto> result = underTest.findByFilter(title, type, city);

        // Verification
        verify(announcementService, times(1)).findAvailableAnnouncementByFilter(title, Arrays.asList(BricolageService.PLOMBERIE, BricolageService.ELECTRICITE), Cities.FES);
        assertEquals(announcementDtos, result);
    }

    @Test
    void testFindById() throws Exception {
        // Mocking data
        Long id = 1L;
        AnnouncementDto announcementDto = new AnnouncementDto();

        when(announcementService.findById(id)).thenReturn(announcementDto);

        // Test
        AnnouncementDto result = underTest.findById(id);

        // Verification
        verify(announcementService, times(1)).findById(id);
        assertEquals(announcementDto, result);
    }

    @Test
    void testFindCommentsByAnnouncementId() throws Exception {
        // Mocking data
        Long id = 1L;
        List<CommentModel> commentModels = new ArrayList<>();

        when(commentService.findCommentsByAnnouncementId(id)).thenReturn(commentModels);

        // Test
        List<CommentModel> result = underTest.findCommentsByAnnouncementId(id);

        // Verification
        verify(commentService, times(1)).findCommentsByAnnouncementId(id);
        assertEquals(commentModels, result);
    }

    @Test
    void testSave() throws Exception {
        // Mocking data
        MultipartFile[] files = {new MockMultipartFile("file", "file.txt", "text/plain", "file content".getBytes())};
        String title = "Test Title";
        String description = "Test Description";
        LocalDate appropriateDate = LocalDate.now();
        String[] typeService = {"PLOMBERIE", "ELECTRICITE"};
        String city = "FES";
        Long idUser = 1L;

        when(announcementService.save(any(AnnouncementDto.class), eq(files))).thenReturn(new Announcement());

        // Test
        Announcement result = underTest.save(files, title, description, appropriateDate, typeService, city, idUser);

        // Verification
        verify(announcementService, times(1)).save(any(AnnouncementDto.class), eq(files));
        assertEquals(Announcement.class, result.getClass());
    }

    @Test
    void testUpdate() throws Exception {
        // Mocking data
        MultipartFile[] files = {new MockMultipartFile("file", "file.txt", "text/plain", "file content".getBytes())};
        Long id = 1L;
        Long idUser = 2L;
        String description = "Updated Description";

        when(announcementService.update(any(AnnouncementDto.class), eq(files))).thenReturn(new AnnouncementDto());

        // Test
        AnnouncementDto result = underTest.update(files, id, idUser, description);

        // Verification
        verify(announcementService, times(1)).update(any(AnnouncementDto.class), eq(files));
        assertEquals(AnnouncementDto.class, result.getClass());
    }

    @Test
    void testDelete() throws Exception {
        // Mocking data
        AnnouncementDto dto = new AnnouncementDto();

        doNothing().when(announcementService).deleteById(dto.getId(), dto.getIdUser());

        // Test
        underTest.delete(dto);

        // Verification
        verify(announcementService, times(1)).deleteById(dto.getId(), dto.getIdUser());
    }
}