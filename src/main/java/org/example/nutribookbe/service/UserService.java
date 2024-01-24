package org.example.nutribookbe.service;

import org.example.nutribookbe.entity.User;
import org.example.nutribookbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;
    }
    //CREATE USER
    public User createUser(User user) {
        //Check if username already exists
        if (repo.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        //check if email already exists
        if (repo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        //save user
        return repo.save(user);
    }

    //DELETE USER
    public void deleteUser(String id) {
        User user = getUser(id);
        repo.delete(user);
    }

    //FIND User BY X
    public Optional<User> findByID(String id) {
        return repo.findById(id);
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }


    //UPDATE USER DETAILS
    public User updateUsername(String id, String newUsername) {
        //Find existing User by id
        User user = getUser(id);

        //Validate new username
        if (newUsername == null || newUsername.isEmpty()) {
            throw new IllegalArgumentException("New username cannot be empty");
        }
        //Check if new username already exists
        if (repo.existsByUsername(newUsername)) {
            throw new IllegalArgumentException("Username already exists");
        }

        //Set the new username
        user.setUsername(newUsername);

        //Save change to User
        return repo.save(user);
    }

    public User updateEmail(String id, String newEmail) {
        //Find existing User by id
        User user = getUser(id);

        //Validate new email
        if (newEmail == null || newEmail.isEmpty()) {
            throw new IllegalArgumentException("New email cannot be empty");
        }
        //Check if new email already exists
        if (repo.existsByUsername(newEmail)) {
            throw new IllegalArgumentException("Email already exists");
        }

        //set new email
        user.setEmail(newEmail);

        //Save change to User
        return repo.save(user);
    }

    public User updatePassword(String id, String newPassword) {
        //Find existing User by id
        User user = getUser(id);

        //Password validation should be done by front end (I hope, as long as it works)

        //Set new password
        user.setPassword(newPassword);

        //Save change to User
        return repo.save(user);
    }

    //Used this bit 3 times thought I should separate it out
    private User getUser(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

}
