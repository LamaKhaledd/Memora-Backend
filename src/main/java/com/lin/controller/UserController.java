package com.lin.controller;

import com.lin.entity.User;
import com.lin.service.LoginService;
import com.lin.service.RegisterService;
import com.lin.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    LoginService loginService;
    @Autowired
    RegisterService registerService;

    @Autowired
    UserService userService;

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

    @PutMapping(value = "/update-parent")
    public User updateParent(@RequestBody User updatedUser) {
        return userService.updateParent(updatedUser);
    }

    @GetMapping(value = "/highest-flashcards")
    public List<User> getTop3UsersWithHighestFlashcardsCount() {
        return userService.getTop3UsersWithHighestFlashcardsCount();
    }

}
