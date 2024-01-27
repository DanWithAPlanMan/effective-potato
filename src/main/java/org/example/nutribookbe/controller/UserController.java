package org.example.nutribookbe.controller;

import org.example.nutribookbe.entity.User;
import org.example.nutribookbe.repository.UserRepository;
import org.example.nutribookbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserRepository repo;

    @Autowired
    public UserController(UserService userService, UserRepository repo) {this.userService = userService;
        this.repo = repo;
    }

    @GetMapping
    public String hello(){
        return "Hello";
    }

    /*//Registration Handling
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if ((user.getUsername()==null||repo.existsByUsername(user.getUsername())) ||
                (user.getEmail()==null|| repo.existsByEmail(user.getEmail())) ||
                (user.getPassword()==null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }*/

    //New Registration Handling
    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (user.getUsername() == null) {
            return new ResponseEntity<>("Username cannot be null", HttpStatus.BAD_REQUEST);
        } else if (repo.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>("Username is already in use", HttpStatus.BAD_REQUEST);
        } else if (user.getEmail()==null) {
            return new ResponseEntity<>("Email cannot be null", HttpStatus.BAD_REQUEST);
        } else if (repo.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST);
        } else if (user.getPassword()==null) {
            return new ResponseEntity<>("Password cannot be null", HttpStatus.BAD_REQUEST);
        }
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(user.getUsername()+ " created successfully.");
    }



    //Change Username
    @PutMapping("/{id}/update-username")
    public ResponseEntity<User> updateUsername(@PathVariable String id, @RequestBody String newUsername) {
        User updatedUser = userService.updateUsername(id, newUsername);
        return ResponseEntity.ok(updatedUser);
    }

    //Get User by ID
    @GetMapping("/get-user/{id}")
    public Optional<User> getUserById(@PathVariable(value = "id") String id) {
        return userService.findByID(id);
    }

    //Delete User by ID
    @DeleteMapping("/{id}/delete-user")
    public String deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return "User Deleted";
    }

    //Get User by Email
    @GetMapping("/get-user/email")
    public User getUserByEmail(@RequestParam(value = "email") String email) {
        return userService.findByEmail(email);
    }

    //Change Email
    @PutMapping("/{id}/update-email")
    public ResponseEntity<User> updateEmail(@PathVariable(value = "id") String id, @RequestBody String newEmail) {
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
