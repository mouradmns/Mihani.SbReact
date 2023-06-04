package com.mihani.rest;

import com.mihani.dtos.AnnouncementDto;
import com.mihani.entities.Admin;
import com.mihani.entities.User;
import com.mihani.services.AdminService;
import com.mihani.services.AnnouncementService;
import com.mihani.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
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
    public List<AnnouncementDto> findNonValidatedAnnouncements() {
        return announcementService.findNonValidatedAnnouncements();
    }

    @PutMapping("/announcements")
    public void acceptAnnouncement(@RequestBody AnnouncementDto announcementDto) throws Exception {
        announcementService.acceptAnnouncement(announcementDto.getId());
    }

    @GetMapping("/users")
    public List<User> getAllUsers (@RequestParam(value = "available", required = false) Boolean available) {
            return userService.findAllUsersByAvailable(available);
    }

    @PutMapping("/users")
    public User blockUser(@RequestBody User user) throws Exception {
        return userService.blockUser(user.getId(), user.getAvailable());
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) throws Exception {
        userService.deleteUser(id);
    }
}
