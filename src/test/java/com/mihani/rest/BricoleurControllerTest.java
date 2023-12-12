package com.mihani.rest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mihani.dtos.BricoleurProfileDto;
import com.mihani.entities.Bricoleur;
import com.mihani.exceptions.BricoleurAlreadyExistsException;
import com.mihani.exceptions.BricoleurNotFoundException;
import com.mihani.mappers.BricoleurMapperImpl;
import com.mihani.services.BricoleurServiceImpl;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

@ContextConfiguration(classes = {BricoleurController.class})
@ExtendWith(SpringExtension.class)
class BricoleurControllerTest {
    @Autowired
    private BricoleurController bricoleurController;

    @MockBean
    private BricoleurMapperImpl bricoleurMapperImpl;

    @MockBean
    private BricoleurServiceImpl bricoleurServiceImpl;

    /**
     * Method under test: {@link BricoleurController#deleteBricoleur(Long)}
     */
    @Test
    void testDeleteBricoleur() throws BricoleurNotFoundException, Exception {
        doNothing().when(bricoleurServiceImpl).deleteBricoleur(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/bricoleurs/{id}", 1L);
        MockMvcBuilders.standaloneSetup(bricoleurController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BricoleurController#bricoleurs()}
     */
    @Test
    void testBricoleurs() throws Exception {
        when(bricoleurServiceImpl.listBricoleurs()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bricoleurs");
        MockMvcBuilders.standaloneSetup(bricoleurController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test:
     * {@link BricoleurController#Filteredbricoleurs(List, String)}
     */
    @Test
    void testFilteredbricoleurs() throws Exception {
        when(bricoleurServiceImpl.filteredlistOfAVailableBricoleurs(Mockito.<List<String>>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bricoleurs/available");
        MockMvcBuilders.standaloneSetup(bricoleurController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
