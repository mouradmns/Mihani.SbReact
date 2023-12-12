package com.mihani.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CitiesController.class})
@ExtendWith(SpringExtension.class)
class CitiesControllerTest {
    @Autowired
    private CitiesController citiesController;

    /**
     * Method under test: {@link CitiesController#getCities()}
     */
    @Test
    void testGetCities() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cities");
        MockMvcBuilders.standaloneSetup(citiesController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[\"TAROUDANT\",\"AGADIR\",\"MARRAKECH\",\"CASABLANCA\",\"RABAT\",\"TANGER\",\"FES\",\"MEKNES\",\"OUJDA\",\"BERKANE\",\"NADOR"
                                        + "\",\"TETOUAN\",\"KENITRA\",\"SAFI\",\"ESSAOUIRA\",\"LAAYOUNE\",\"DAKHLA\",\"AUTRE\"]"));
    }

    /**
     * Method under test: {@link CitiesController#getCities()}
     */
    @Test
    void testGetCities2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cities", "Uri Variables");
        MockMvcBuilders.standaloneSetup(citiesController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[\"TAROUDANT\",\"AGADIR\",\"MARRAKECH\",\"CASABLANCA\",\"RABAT\",\"TANGER\",\"FES\",\"MEKNES\",\"OUJDA\",\"BERKANE\",\"NADOR"
                                        + "\",\"TETOUAN\",\"KENITRA\",\"SAFI\",\"ESSAOUIRA\",\"LAAYOUNE\",\"DAKHLA\",\"AUTRE\"]"));
    }
}
