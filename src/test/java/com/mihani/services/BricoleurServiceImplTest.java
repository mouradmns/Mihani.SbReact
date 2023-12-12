package com.mihani.services;

import com.mihani.dtos.BricoleurProfileDto;
import com.mihani.entities.Bricoleur;
import com.mihani.exceptions.BricoleurAlreadyExistsException;
import com.mihani.exceptions.BricoleurNotFoundException;
import com.mihani.mappers.BricoleurMapperImpl;
import com.mihani.repositories.BricoleurRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BricoleurServiceImplTest {

    @Mock
    private BricoleurRepo bricoleurRepo;

    @Mock
    private BricoleurMapperImpl dtoMapper;

    @InjectMocks
    private BricoleurServiceImpl bricoleurService;

    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void listBricoleurs() {

        Bricoleur bricoleur1 = new Bricoleur();
        Bricoleur bricoleur2 = new Bricoleur();

        BricoleurProfileDto dto1 = new BricoleurProfileDto();
        BricoleurProfileDto dto2 = new BricoleurProfileDto();

        List<Bricoleur> mockBricoleurs = Arrays.asList(bricoleur1, bricoleur2);
        List<BricoleurProfileDto> mockDtos = Arrays.asList(dto1, dto2);

        when(bricoleurRepo.findAll()).thenReturn(mockBricoleurs);
        when(dtoMapper.fromBricoleur(bricoleur1)).thenReturn(dto1);
        when(dtoMapper.fromBricoleur(bricoleur2)).thenReturn(dto2);

        List<BricoleurProfileDto> result = bricoleurService.listBricoleurs();

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
    }

    @Test
    void filteredlistOfAVailableBricoleurs() {

        // Arrange
        List<String> services = Arrays.asList("PLOMBERIE");
        String description = "bric";
        List<Bricoleur> mockBricoleurs = Arrays.asList(new Bricoleur(), new Bricoleur());

        when(bricoleurRepo.findAll((Specification<Bricoleur>) any())).thenReturn(mockBricoleurs);
        when(dtoMapper.fromBricoleur(any())).thenReturn(new BricoleurProfileDto());

        // Act
        List<BricoleurProfileDto> result = bricoleurService.filteredlistOfAVailableBricoleurs(services, description);

        // Assert
        assertNotNull(result);
        //verify
        verify(bricoleurRepo).findAll((Specification<Bricoleur>) any());
    }

    @Test
    void getBricoleurExistingId_ReturnsBricoleurProfileDto() throws BricoleurAlreadyExistsException {
        // Arrange
        Bricoleur bricoleurToSave = new Bricoleur();
        when(bricoleurRepo.save(bricoleurToSave)).thenReturn(bricoleurToSave);

        // Act
        Bricoleur result = bricoleurService.saveBricoleur(bricoleurToSave);

        // Assert
        assertNotNull(result);
    }
    @Test
    public void testGetBricoleur_NonExistingId_ThrowsBricoleurNotFoundException() {
        // Arrange
        Long nonExistingId = 2L;
        when(bricoleurRepo.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BricoleurNotFoundException.class, () -> bricoleurService.getBricoleur(nonExistingId));
    }


    @Test
    void saveBricoleur() throws BricoleurAlreadyExistsException {
        // Arrange
        Bricoleur bricoleurToSave = new Bricoleur();
        when(bricoleurRepo.save(bricoleurToSave)).thenReturn(bricoleurToSave);

        // Act
        Bricoleur result = bricoleurService.saveBricoleur(bricoleurToSave);

        // Assert
        assertNotNull(result);
    }

    @Test
    void updateBricoleur_ExistingBricoleur() throws BricoleurNotFoundException {
        // Arrange
        Bricoleur existingBricoleur = new Bricoleur();
        when(bricoleurRepo.findById(existingBricoleur.getId())).thenReturn(Optional.of(existingBricoleur));
        when(bricoleurRepo.save(existingBricoleur)).thenReturn(existingBricoleur);

        // Act
        Bricoleur result = bricoleurService.updateBricoleur(existingBricoleur);

        // Assert
        assertNotNull(result);
        // Add more assertions based on your requirements
    }
    @Test
    public void testUpdateBricoleurNonExistingBricoleur_ThrowsBricoleurNotFoundException() {
        // Arrange
        Bricoleur nonExistingBricoleur = new Bricoleur();
        when(bricoleurRepo.findById(nonExistingBricoleur.getId())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BricoleurNotFoundException.class, () -> bricoleurService.updateBricoleur(nonExistingBricoleur));
    }

    @Test
    void deleteBricoleurExistingBricoleur() throws BricoleurNotFoundException {
        // Arrange
        Long existingId = 1L;
        when(bricoleurRepo.findById(existingId)).thenReturn(Optional.of(new Bricoleur()));

        // Act
        bricoleurService.deleteBricoleur(existingId);

        // Assert
        // Verify that deleteById was called
        verify(bricoleurRepo, times(1)).deleteById(existingId);

    }
    @Test
    public void testDeleteBricoleur_NonExistingId_ThrowsBricoleurNotFoundException() {
        // Arrange
        Long nonExistingId = 2L;
        when(bricoleurRepo.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BricoleurNotFoundException.class, () -> bricoleurService.deleteBricoleur(nonExistingId));
    }

}