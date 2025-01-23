package com.lin.controller;

import com.lin.entity.User;
import com.lin.repository.UserRepository;
import com.lin.service.LoginService;
import com.lin.service.RegisterService;
import com.lin.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    LoginService loginService;

    @Autowired
    RegisterService registerService;

    @Autowired
    UserRepository userRepository;


    // Fetch user by userId
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Update user details
    @PostMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    // Delete a user by userId
    @DeleteMapping("/tasks/{userId}")
    public void deleteUserById(@PathVariable String userId) {
        userService.deleteUserById(userId);
    }

    /////////// auth

    @PostMapping(value = "/send")
    public String send(@RequestBody User user) {
        return registerService.send(user);
    }

    @PostMapping(value = "/register")
    public String registerUser(@RequestBody User user, @RequestParam("token") String token) {
        return registerService.registerUser(user, token);
    }

    @PostMapping(value = "/login")
    public String loginUser(@RequestBody User user) {
        return loginService.loginUser(user);
    }

    @GetMapping(value = "/email")
    public User getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    // New method to get user ID by email
    @GetMapping("/id-by-email")
    public String getUserIdByEmail(@RequestParam("email") String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getUserId();  // Assuming user.getId() returns the userId
        } else {
            return "User not found";
        }
    }
    ////////////////////// lama

    @PutMapping(value = "/update-parent")
    public User updateParent(@RequestBody User updatedUser) {
        return userService.updateParent(updatedUser);
    }

    @GetMapping(value = "/highest-flashcards")
    public List<User> getTop3UsersWithHighestFlashcardsCount() {
        return userService.getTop3UsersWithHighestFlashcardsCount();
    }


//////////////////////
/*
    // Get all students of an instructor based on instructorId
    @GetMapping("/students/instructor/{instructorId}")
    public List<User> getStudentsByInstructorId(@PathVariable String instructorId) {
        return userService.getStudentsByInstructorId(instructorId);
    }*/


    // Get all students for a given classroomId
    @GetMapping("/students/classroom/{classroomId}")
    public List<User> getStudentsByClassroomId(@PathVariable String classroomId) {
        return userService.getStudentsByClassroomId(classroomId);
    }


    @PostMapping("/findByIds")
    public ResponseEntity<List<User>> getUsersByIds(@RequestBody List<String> userIds) {
        System.out.println("Received user IDs: " + userIds);  // Log userIds for debugging
        try {
            List<User> users = userService.findUsersByIds(userIds);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // Return a 500 error if something goes wrong
        }
    }


}