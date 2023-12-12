package com.mihani.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BricolageServicesServiceImplTest {

    @Test
    void listServices() {
        // Arrange
        BricolageServicesServiceImpl bricolageServicesService = new BricolageServicesServiceImpl();

        // Act
        List<String> services = bricolageServicesService.listServices();

        // Assert
        assertNotNull(services);
        assertEquals(7, services.size()); // Adjust the size based on the number of services in your BricolageService enum
        assertTrue(services.contains("PLOMBERIE"));
        assertTrue(services.contains("ELECTRICITE"));
        assertTrue(services.contains("PEINTRE"));

        assertTrue(services.contains("COMPUTER"));
        assertTrue(services.contains("CLEANING"));
        assertTrue(services.contains("CARPENTER"));
        assertTrue(services.contains("AUTRE"));
    }
}