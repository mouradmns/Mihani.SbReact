package com.mihani.rest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihani.dtos.OfferModel;
import com.mihani.entities.Admin;
import com.mihani.entities.Announcement;
import com.mihani.entities.Bricoleur;
import com.mihani.entities.Cities;
import com.mihani.entities.Offer;
import com.mihani.exceptions.AnnounceNotFoundException;
import com.mihani.exceptions.OfferNotFoundException;
import com.mihani.exceptions.UserNotFoundException;
import com.mihani.services.OfferServiceImpl;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {OfferController.class})
@ExtendWith(SpringExtension.class)
class OfferControllerTest {
  @Autowired
  private OfferController offerController;

  @MockBean
  private OfferServiceImpl offerServiceImpl;

  /**
   * Method under test: {@link OfferController#updateOffer(Long, OfferModel)}
   */
  @Test
  void testUpdateOffer() throws OfferNotFoundException, Exception {
    Announcement announcement = new Announcement();
    announcement.setAnnouncementAttachments(new ArrayList<>());
    announcement.setAppropriateDate(LocalDate.of(1970, 1, 1));
    announcement.setAvailable(true);
    announcement.setCity(Cities.TAROUDANT);
    announcement.setComments(new ArrayList<>());
    announcement.setDateAnnouncement(LocalDate.of(1970, 1, 1));
    announcement.setDescription("The characteristics of someone or something");
    announcement.setId(1L);
    announcement.setOffers(new ArrayList<>());
    announcement.setReports(new ArrayList<>());
    announcement.setTitle("Dr");
    announcement.setTypeService(new ArrayList<>());
    announcement.setUser(new Admin());
    announcement.setValidated(true);

    Bricoleur bricoleur = new Bricoleur();
    bricoleur.setAge(1);
    bricoleur.setAvailable(true);
    bricoleur.setBricoleurAvailability(true);
    bricoleur.setComments(new ArrayList<>());
    bricoleur.setDateInscription(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    bricoleur.setDescription("The characteristics of someone or something");
    bricoleur.setEmail("jane.doe@example.org");
    bricoleur.setId(1L);
    bricoleur.setMainPic("Main Pic");
    bricoleur.setNom("Nom");
    bricoleur.setOffers(new ArrayList<>());
    bricoleur.setPassword("iloveyou");
    bricoleur.setPrenom("Prenom");
    bricoleur.setRating(10.0d);
    bricoleur.setServicePricePerHour(10.0d);
    bricoleur.setServices(new ArrayList<>());
    bricoleur.setTel("Tel");
    bricoleur.setTotalWorkHours(1);
    bricoleur.setVille("Ville");

    Offer offer = new Offer();
    offer.setAnnouncement(announcement);
    offer.setBricoleur(bricoleur);
    offer.setDateOffer(LocalDate.of(1970, 1, 1));
    offer.setDescription("The characteristics of someone or something");
    offer.setId(1L);
    offer.setPrice(10.0d);
    when(offerServiceImpl.updateOffer(Mockito.<Offer>any())).thenReturn(offer);

    OfferModel offerModel = new OfferModel();
    offerModel.setDateOffer(null);
    offerModel.setDescription("The characteristics of someone or something");
    offerModel.setId(1L);
    offerModel.setIdAnnouncement(1L);
    offerModel.setIdUser(1L);
    offerModel.setPrice(10.0d);
    String content = (new ObjectMapper()).writeValueAsString(offerModel);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/offers/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);
    MockMvcBuilders.standaloneSetup(offerController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content()
                    .string(
                            "{\"id\":1,\"description\":\"The characteristics of someone or something\",\"dateOffer\":[1970,1,1],\"price"
                                    + "\":10.0}"));
  }

  /**
   * Method under test: {@link OfferController#deleteBricoleur(Long)}
   */
  @Test
  void testDeleteBricoleur() throws OfferNotFoundException, Exception {
    doNothing().when(offerServiceImpl).deleteOffer(Mockito.<Long>any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/offers/{id}", 1L);
    MockMvcBuilders.standaloneSetup(offerController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  /**
   * Method under test: {@link OfferController#listOffersByAnnouncement(Long)}
   */
  @Test
  void testListOffersByAnnouncement() throws AnnounceNotFoundException, Exception {
    when(offerServiceImpl.listAnnouncementOffers(Mockito.<Long>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/offers/announcement/{announcementId}",
            1L);
    MockMvcBuilders.standaloneSetup(offerController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Method under test: {@link OfferController#listOffersByUser(Long)}
   */
  @Test
  void testListOffersByUser() throws UserNotFoundException, Exception {
    when(offerServiceImpl.listBricoleurOffers(Mockito.<Long>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/offers/user/{userId}", 1L);
    MockMvcBuilders.standaloneSetup(offerController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content().string("[]"));
  }
}
