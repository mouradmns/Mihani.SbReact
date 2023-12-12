package com.mihani.services;

import com.mihani.dtos.UserOffersDto;
import com.mihani.entities.Announcement;
import com.mihani.entities.Bricoleur;
import com.mihani.entities.Offer;
import com.mihani.exceptions.AnnounceNotFoundException;
import com.mihani.exceptions.OfferNotFoundException;
import com.mihani.exceptions.UserNotFoundException;
import com.mihani.mappers.UserOffersMapper;
import com.mihani.repositories.AnnouncementRepo;
import com.mihani.repositories.BricoleurRepo;
import com.mihani.repositories.OfferRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

class OfferServiceImplTest {

    @Mock
    private AnnouncementRepo announcementRepo;

    @Mock
    private BricoleurRepo bricoleurRepo;
    @Mock
    private OfferRepo offerRepo;

    @Mock
    private UserOffersMapper offMapper;

    @InjectMocks
    private OfferServiceImpl offerService;

    @Test
    void addOffer() throws Exception, UserNotFoundException, AnnounceNotFoundException {
        // Arrange
        Long idAnnouncement = 1L;
        Long idBricoleur = 2L;
        Offer offer = new Offer();
        Bricoleur bricoleur = new Bricoleur();
        Announcement announcement = new Announcement();


        when(bricoleurRepo.findById(idBricoleur)).thenReturn(Optional.of(bricoleur));
        when(announcementRepo.findById(idAnnouncement)).thenReturn(Optional.of(announcement));
        when(offerRepo.save(Mockito.any(Offer.class))).thenReturn(offer);

        // Act
        Offer result = offerService.addOffer(idAnnouncement, idBricoleur, offer);

        // Assert
        assertNotNull(result);
        assertEquals(offer, result);
    }

    @Test
    void updateOffer() throws OfferNotFoundException {
        // Arrange
        Offer existingOffer = new Offer();
        existingOffer.setId(1L);
        existingOffer.setDescription("Old description");

        when(offerRepo.findById(existingOffer.getId())).thenReturn(Optional.of(existingOffer));
        when(offerRepo.save(Mockito.any(Offer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        existingOffer.setDescription("New description");
        Offer result = offerService.updateOffer(existingOffer);

        // Assert
        assertNotNull(result);
        assertEquals("New description", result.getDescription());
    }

    @Test
    void deleteOffer() {
        // Arrange
        Long idOffer = 1L;
        Offer existingOffer = new Offer();

        when(offerRepo.findById(idOffer)).thenReturn(Optional.of(existingOffer));
        doNothing().when(offerRepo).deleteById(idOffer);

        // Act
        assertDoesNotThrow(() -> offerService.deleteOffer(idOffer));

    }

    @Test
    void listAnnouncementOffers() throws AnnounceNotFoundException {
        Long idAnnouncement = 1L;
        Announcement announcement = new Announcement();
        announcement.setId(idAnnouncement);
        Offer offer = new Offer();
        offer.setId(1L);
        offer.setDescription("Offer 1");

        when(announcementRepo.findById(idAnnouncement)).thenReturn(Optional.of(announcement));
        when(offerRepo.getOffersByAnnouncement_Id(idAnnouncement)).thenReturn(Arrays.asList(offer));
        when(offMapper.fromOffer(offer)).thenReturn(new UserOffersDto(offer.getId(), offer.getDescription()));

        // Act
        List<UserOffersDto> result = offerService.listAnnouncementOffers(idAnnouncement);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(offer.getId(), result.get(0).getIdOffer());
        assertEquals(offer.getDescription(), result.get(0).getDescription());
    }

    @Test
    void listBricoleurOffers() throws UserNotFoundException {
        // Arrange
        Long idBricoleur = 1L;
        Bricoleur bricoleur = new Bricoleur();
        bricoleur.setId(idBricoleur);
        Offer offer = new Offer();
        offer.setId(1L);
        offer.setDescription("Offer 1");

        when(bricoleurRepo.findById(idBricoleur)).thenReturn(Optional.of(bricoleur));
        when(offerRepo.getOffersByBricoleur(bricoleur)).thenReturn(Arrays.asList(offer));
        when(offMapper.fromOffer(offer)).thenReturn(new UserOffersDto(offer.getId(), offer.getDescription()));

        // Act
        List<UserOffersDto> result = offerService.listBricoleurOffers(idBricoleur);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(offer.getId(), result.get(0).getIdOffer());
        assertEquals(offer.getDescription(), result.get(0).getDescription());

    }
}