package com.mihani.rest;

import com.mihani.dtos.AnnouncementDto;
import com.mihani.entities.Admin;
import com.mihani.entities.User;
import com.mihani.services.AdminService;
import com.mihani.services.AnnouncementService;
import com.mihani.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRest {


    @Autowired

    private AnnouncementService announcementService;

    @Autowired
    private IUserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/{id}")

    public Admin getAdmin(@PathVariable("id") Long id) throws Exception {
        return adminService.getAdmin(id);
    }

    @GetMapping("/announcements")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public List<AnnouncementDto> findNonValidatedAnnouncements() {
        return announcementService.findNonValidatedAnnouncements();
    }

    @PutMapping("/announcements")
    @PreAuthorize("hasAnyAuthority('admin:update')")
    public void acceptAnnouncement(@RequestBody AnnouncementDto announcementDto) throws Exception {
        announcementService.acceptAnnouncement(announcementDto.getId());
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('admin:read')")
    public List<User> getAllUsers (@RequestParam(value = "available", required = false) Boolean available) {
            return userService.findAllUsersByAvailable(available);
    }

    @PutMapping("/users")
    @PreAuthorize("hasAnyAuthority('admin:update')")
    public User blockUser(@RequestBody User user) throws Exception {
        return userService.blockUser(user.getId(), user.getAvailable());
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('admin:delete')")
    public void deleteUser(@PathVariable("id") Long id) throws Exception {
        userService.deleteUser(id);
    }
}
