package com.mihani.rest;

import com.mihani.dtos.AnnouncementDto;
import com.mihani.entities.User;
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

    @GetMapping("/announcements")
    public List<AnnouncementDto> findAllAnnouncements(@RequestParam(value = "available", required = false) boolean available) {
        return announcementService.findAllAnnouncementsByAvailable(available);
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
