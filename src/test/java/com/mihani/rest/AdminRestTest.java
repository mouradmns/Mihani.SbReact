package com.mihani.rest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihani.dtos.AnnouncementDto;
import com.mihani.entities.Admin;
import com.mihani.entities.Announcement;
import com.mihani.entities.AnnouncementAttachment;
import com.mihani.entities.Cities;
import com.mihani.entities.User;
import com.mihani.services.AdminService;
import com.mihani.services.AnnouncementService;
import com.mihani.services.IUserService;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AdminRest.class})
@ExtendWith(SpringExtension.class)
class AdminRestTest {
  @Autowired
  private AdminRest adminRest;

  @MockBean
  private AdminService adminService;

  @MockBean
  private AnnouncementService announcementService;

  @MockBean
  private IUserService iUserService;

  /**
   * Method under test: {@link AdminRest#getAdmin(Long)}
   */
  @Test
  void testGetAdmin() throws Exception {
    Admin admin = new Admin();
    admin.setAge(1);
    admin.setAvailable(true);
    admin.setComments(new ArrayList<>());
    admin.setDateInscription(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    admin.setEmail("jane.doe@example.org");
    admin.setId(1L);
    admin.setMainPic("Main Pic");
    admin.setNom("Nom");
    admin.setPassword("iloveyou");
    admin.setPrenom("Prenom");
    admin.setTel("Tel");
    admin.setVille("Ville");
    when(adminService.getAdmin(Mockito.<Long>any())).thenReturn(admin);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/{id}", 1L);
    MockMvcBuilders.standaloneSetup(adminRest)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content()
                    .string(
                            "{\"id\":1,\"prenom\":\"Prenom\",\"nom\":\"Nom\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\",\"age\":1,"
                                    + "\"tel\":\"Tel\",\"ville\":\"Ville\",\"dateInscription\":0,\"mainPic\":\"Main Pic\",\"available\":true,\"comments\":[]}"));
  }

  /**
   * Method under test: {@link AdminRest#getAdmin(Long)}
   */
  @Test
  void testGetAdmin2() throws Exception {
    Admin admin = new Admin();
    admin.setAge(1);
    admin.setAvailable(true);
    admin.setComments(new ArrayList<>());
    admin.setDateInscription(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    admin.setEmail("jane.doe@example.org");
    admin.setId(1L);
    admin.setMainPic("Main Pic");
    admin.setNom("Nom");
    admin.setPassword("iloveyou");
    admin.setPrenom("Prenom");
    admin.setTel("Tel");
    admin.setVille("Ville");
    when(adminService.getAdmin(Mockito.<Long>any())).thenReturn(admin);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/{id}", "Uri Variables",
            "Uri Variables");
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminRest).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
  }

  /**
   * Method under test: {@link AdminRest#getAdmin(Long)}
   */
  @Test
  void testGetAdmin3() throws Exception {
    Admin admin = new Admin();
    admin.setAge(1);
    admin.setAvailable(true);
    admin.setComments(new ArrayList<>());
    admin.setDateInscription(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    admin.setEmail("jane.doe@example.org");
    admin.setId(1L);
    admin.setMainPic("Main Pic");
    admin.setNom("Nom");
    admin.setPassword("iloveyou");
    admin.setPrenom("Prenom");
    admin.setTel("Tel");
    admin.setVille("Ville");
    when(adminService.getAdmin(Mockito.<Long>any())).thenReturn(admin);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/{id}", 1L);
    requestBuilder.accept("https://example.org/example");
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminRest).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
  }

  /**
   * Method under test: {@link AdminRest#findNonValidatedAnnouncements()}
   */
  @Test
  void testFindNonValidatedAnnouncements() throws Exception {
    when(announcementService.findNonValidatedAnnouncements()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/announcements");
    MockMvcBuilders.standaloneSetup(adminRest)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Method under test: {@link AdminRest#findNonValidatedAnnouncements()}
   */
  @Test
  void testFindNonValidatedAnnouncements2() throws Exception {
    when(announcementService.findNonValidatedAnnouncements()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/announcements");
    requestBuilder.accept("https://example.org/example");
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminRest).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
  }

  /**
   * Method under test: {@link AdminRest#findNonValidatedAnnouncements()}
   */
  @Test
  void testFindNonValidatedAnnouncements3() throws Exception {
    when(announcementService.findNonValidatedAnnouncements()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/announcements");
    requestBuilder.accept("");
    MockMvcBuilders.standaloneSetup(adminRest)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Method under test: {@link AdminRest#findNonValidatedAnnouncements()}
   */
  @Test
  void testFindNonValidatedAnnouncements4() throws Exception {
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

    AnnouncementAttachment announcementAttachment = new AnnouncementAttachment();
    announcementAttachment.setAnnouncement(announcement);
    announcementAttachment.setId(1L);
    announcementAttachment.setPath("Path");

    ArrayList<AnnouncementAttachment> announcementAttachments = new ArrayList<>();
    announcementAttachments.add(announcementAttachment);
    AnnouncementDto.AnnouncementDtoBuilder announcementAttachmentsResult = AnnouncementDto.builder()
            .announcementAttachments(announcementAttachments);
    AnnouncementDto.AnnouncementDtoBuilder cityResult = announcementAttachmentsResult
            .appropriateDate(LocalDate.of(1970, 1, 1))
            .available(true)
            .city(Cities.TAROUDANT);
    AnnouncementDto.AnnouncementDtoBuilder titleResult = cityResult.dateAnnouncement(LocalDate.of(1970, 1, 1))
            .description("The characteristics of someone or something")
            .id(1L)
            .idUser(1L)
            .title("Dr");
    AnnouncementDto buildResult = titleResult.typeService(new ArrayList<>())
            .userProfileImage("User Profile Image")
            .username("janedoe")
            .validated(true)
            .build();

    ArrayList<AnnouncementDto> announcementDtoList = new ArrayList<>();
    announcementDtoList.add(buildResult);
    when(announcementService.findNonValidatedAnnouncements()).thenReturn(announcementDtoList);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/announcements");
    MockMvcBuilders.standaloneSetup(adminRest)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content()
                    .string(
                            "[{\"id\":1,\"title\":\"Dr\",\"typeService\":[],\"city\":\"TAROUDANT\",\"description\":\"The characteristics of someone"
                                    + " or something\",\"appropriateDate\":[1970,1,1],\"available\":true,\"validated\":true,\"dateAnnouncement\":[1970"
                                    + ",1,1],\"announcementAttachments\":[{\"id\":1,\"path\":\"Path\"}],\"idUser\":1,\"username\":\"janedoe\",\"userProfileImage"
                                    + "\":\"User Profile Image\"}]"));
  }

  /**
   * Method under test: {@link AdminRest#deleteUser(Long)}
   */
  @Test
  void testDeleteUser() throws Exception {
    doNothing().when(iUserService).deleteUser(Mockito.<Long>any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/admin/users/{id}", 1L);
    MockMvcBuilders.standaloneSetup(adminRest)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  /**
   * Method under test: {@link AdminRest#deleteUser(Long)}
   */
  @Test
  void testDeleteUser2() throws Exception {
    doNothing().when(iUserService).deleteUser(Mockito.<Long>any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/admin/users/{id}", "Uri Variables",
            "Uri Variables");
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminRest).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
  }

  /**
   * Method under test: {@link AdminRest#deleteUser(Long)}
   */
  @Test
  void testDeleteUser3() throws Exception {
    doNothing().when(iUserService).deleteUser(Mockito.<Long>any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/admin/users/{id}", 1L);
    requestBuilder.accept("https://example.org/example");
    MockMvcBuilders.standaloneSetup(adminRest)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  /**
   * Method under test: {@link AdminRest#blockUser(User)}
   */
  @Test
  void testBlockUser() throws Exception {
    MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/admin/users");
    putResult.characterEncoding("https://example.org/example");
    MockHttpServletRequestBuilder contentTypeResult = putResult.contentType(MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    MockHttpServletRequestBuilder requestBuilder = contentTypeResult
            .content(objectMapper.writeValueAsString(new Admin()));
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminRest).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
  }

  /**
   * Method under test: {@link AdminRest#getAllUsers(Boolean)}
   */
  @Test
  void testGetAllUsers() throws Exception {
    when(iUserService.findAllUsersByAvailable(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/users");
    MockMvcBuilders.standaloneSetup(adminRest)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Method under test: {@link AdminRest#getAllUsers(Boolean)}
   */
  @Test
  void testGetAllUsers2() throws Exception {
    when(iUserService.findAllUsersByAvailable(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/users");
    requestBuilder.accept("https://example.org/example");
    ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminRest).build().perform(requestBuilder);
    actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
  }

  /**
   * Method under test: {@link AdminRest#getAllUsers(Boolean)}
   */
  @Test
  void testGetAllUsers3() throws Exception {
    when(iUserService.findAllUsersByAvailable(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/users");
    requestBuilder.accept("");
    MockMvcBuilders.standaloneSetup(adminRest)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(MockMvcResultMatchers.content().string("[]"));
  }
}
