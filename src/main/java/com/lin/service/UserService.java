package com.lin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.entity.User;
import com.lin.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String userId, String username, String about) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(username);
        user.setAbout(about);
        return userRepository.save(user);
    }

    public List<User> getTop3UsersWithHighestFlashcardsCount() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            return List.of(); 
        }
    
        return allUsers.stream()
                .sorted((user1, user2) -> Integer.compare(user2.getFlashcardsCount(), user1.getFlashcardsCount()))
                .limit(3) 
                .collect(Collectors.toList());
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateParent(User updatedUser) {
        User existingUser = userRepository.findByEmail(updatedUser.getEmail());
        if (existingUser == null) {
            throw new RuntimeException("User not found with email: " + updatedUser.getEmail());
        }
    
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setLocation(updatedUser.getLocation());
        existingUser.setTelephone(updatedUser.getTelephone());
    
        return userRepository.save(existingUser);
    }
    

}
