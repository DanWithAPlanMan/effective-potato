package org.example.nutribookbe.controller;

import org.example.nutribookbe.entity.User;
import org.example.nutribookbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    //Registration Handling
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    //Change Username
    @PutMapping("/{id}/update-username")
    public ResponseEntity<User> updateUsername(@PathVariable String id, @RequestBody String newUsername) {
        User updatedUser = userService.updateUsername(id, newUsername);
        return ResponseEntity.ok(updatedUser);
    }

    //Change Email
    @PutMapping("/{id}/update-email")
    public ResponseEntity<User> updateEmail(@PathVariable String id, @RequestBody String newEmail) {
        User updatedUser = userService.updateEmail(id, newEmail);
        return ResponseEntity.ok(updatedUser);
    }

    //Change Password
    @PutMapping("/{id}/update-password")
    public ResponseEntity<User> updatePassword(@PathVariable String id, @RequestBody String newPassword) {
        User updatedUser = userService.updatePassword(id, newPassword);
        return ResponseEntity.ok(updatedUser);
    }
}
