package com.lin.controller;

import com.lin.entity.User;
import com.lin.service.LoginService;
import com.lin.service.RegisterService;
import com.lin.service.UserService;
import com.lin.DTO.RoleRequest;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    RegisterService roleService;


    @Autowired
    RegisterService adminRequestService;
    @PostMapping("/admin/request")
    public ResponseEntity<String> submitAdminRequest(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String username = request.get("username");

        if (email == null || username == null) {
            return ResponseEntity.badRequest().body("Email and username are required.");
        }

        String response = adminRequestService.submitAdminRequest(email, username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/roleSelect")
    public ResponseEntity<String> assignRole(
            @RequestParam String token,
            @RequestBody RoleRequest roleRequest) {

        String response = roleService.assignRole(
                roleRequest.getEmail(),
                roleRequest.getUsername(),
                roleRequest.getPassword(),
                roleRequest.getRole(),
                token
        );

        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @PostMapping(value = "/send")
    public String send(@RequestBody User user) {
        return registerService.send(user);
    }

    @PostMapping(value = "/register")
    public String registerUser(@RequestBody User user, @RequestParam("token") String token) {
        return registerService.registerUser(user, token);
    }

    @PostMapping(value = "/login")
    public Map<String, Object> loginUser(@RequestBody User user) {
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
