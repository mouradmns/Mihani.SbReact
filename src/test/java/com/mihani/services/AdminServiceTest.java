package com.mihani.services;

import com.mihani.entities.Admin;
import com.mihani.repositories.AdminRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepo adminRepo;

    @InjectMocks
    private AdminService adminService;

    @Test
    void testGetAdminSuccess() throws Exception {
        // Arrange
        Long adminId = 1L;
        Admin mockAdmin = new Admin();
        when(adminRepo.findById(adminId)).thenReturn(Optional.of(mockAdmin));

        // Act
        Admin result = adminService.getAdmin(adminId);

        // Assert
        assertNotNull(result);
        assertEquals(mockAdmin, result);
    }

    @Test
    void testGetAdminNotFound() {
        // Arrange
        Long adminId = 2L;
        when(adminRepo.findById(adminId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(Exception.class, () -> adminService.getAdmin(adminId));
    }

}