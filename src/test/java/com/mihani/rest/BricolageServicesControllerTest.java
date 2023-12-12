package com.mihani.rest;

import com.mihani.services.BricolageServicesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BricolageServicesControllerTest {
    @Mock
    private BricolageServicesServiceImpl bricolageServicesService;

    @InjectMocks
    private BricolageServicesController bricolageServicesController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bricolageServicesController).build();
    }

    @Test
    void testListServices() throws Exception {
        // Mocking
        List<String> mockServiceList = Arrays.asList("Service1", "Service2");
        when(bricolageServicesService.listServices()).thenReturn(mockServiceList);

        // Testing
        mockMvc.perform(MockMvcRequestBuilders.get("/services")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("Service1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value("Service2"));

        // Verification
        verify(bricolageServicesService, times(1)).listServices();
    }
}