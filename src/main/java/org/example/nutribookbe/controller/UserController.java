package org.example.nutribookbe.controller;

import org.example.nutribookbe.dto.UserPostDTO;
import org.example.nutribookbe.entity.User;
import org.example.nutribookbe.repository.UserRepository;
import org.example.nutribookbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }





    //New Registration Handling
    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserPostDTO newUserDTO) {
        if (newUserDTO.getUsername() == null) {
            return new ResponseEntity<>("Username cannot be null", HttpStatus.BAD_REQUEST);
        } else if (repo.existsByUsername(newUserDTO.getUsername())) {
            return new ResponseEntity<>("Username is already in use", HttpStatus.BAD_REQUEST);
        } else if (newUserDTO.getEmail()==null) {
            return new ResponseEntity<>("Email cannot be null", HttpStatus.BAD_REQUEST);
        } else if (repo.existsByEmail(newUserDTO.getEmail())) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST);
        } else if (newUserDTO.getPassword()==null) {
            return new ResponseEntity<>("Password cannot be null", HttpStatus.BAD_REQUEST);
        }

        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        User newUser = new User(newUserDTO.getUsername(), newUserDTO.getEmail(), encoder.encode(newUserDTO.getPassword()));
        userService.createUser(newUser);
        return new  ResponseEntity<>(newUser.getUsername()+ " created successfully.", HttpStatus.CREATED);
    }



    //Change Username
    @PutMapping("/{id}/update-username")
    public ResponseEntity<User> updateUsername(@PathVariable Long id, @RequestBody String newUsername) {
        User updatedUser = userService.updateUsername(id, newUsername);
        return ResponseEntity.ok(updatedUser);
    }

    //Get User by ID
    @GetMapping("/get-user/{id}")
    public Optional<User> getUserById(@PathVariable(value = "id") Long id) {
        return userService.findByID(id);
    }

    //Delete User by ID
    @DeleteMapping("/{id}/delete-user")
    public String deleteUser(@PathVariable Long id) {
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
    public ResponseEntity<User> updateEmail(@PathVariable(value = "id") Long id, @RequestBody String newEmail) {
        User updatedUser = userService.updateEmail(id, newEmail);
        return ResponseEntity.ok(updatedUser);
    }

    //Change Password
    @PutMapping("/{id}/update-password")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody String newPassword) {
        User updatedUser = userService.updatePassword(id, newPassword);
        return ResponseEntity.ok(updatedUser);
    }
}