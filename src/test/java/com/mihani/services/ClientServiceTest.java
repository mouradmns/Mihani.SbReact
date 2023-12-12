package com.mihani.services;

import com.mihani.entities.Client;
import com.mihani.repositories.ClientRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private ClientRepo clientRepo;

    @InjectMocks
    private ClientService clientService;

    @Test
    void testGetClient() throws Exception {
        // Arrange
        Long clientId = 1L;
        Client mockClient = new Client();
        mockClient.setId(clientId);

        when(clientRepo.findById(clientId)).thenReturn(Optional.of(mockClient));

        // Act
        Client result = clientService.getClient(clientId);

        // Assert
        assertNotNull(result);
        assertEquals(clientId, result.getId());
    }

    @Test
    void testGetClientNotFound() {
        // Arrange
        Long clientId = 2L;

        when(clientRepo.findById(clientId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(Exception.class, () -> clientService.getClient(clientId));
    }

    @Test
    void testUpdateClient() throws Exception {
        // Arrange
        Long clientId = 1L;
        Client existingClient = new Client();
        existingClient.setId(clientId);

        when(clientRepo.findById(clientId)).thenReturn(Optional.of(existingClient));
        when(clientRepo.save(Mockito.any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Client updatedClient = new Client();
        updatedClient.setId(clientId);
        Client result = clientService.updateClient(updatedClient);

        // Assert
        assertNotNull(result);
        assertEquals(clientId, result.getId());
    }

    @Test
    void testUpdateClientNotFound() {
        // Arrange
        Long clientId = 2L;

        when(clientRepo.findById(clientId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(Exception.class, () -> clientService.updateClient(new Client()));
    }

}