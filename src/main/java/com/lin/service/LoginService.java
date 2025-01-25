package com.lin.service;
import com.lin.entity.User;
import com.lin.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public Map<String, Object> loginUser(User user) {
        Map<String, Object> response = new HashMap<>();
        
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            if (encoder.matches(user.getPassword(), existingUser.getPassword())) {
                response.put("role", existingUser.getRole().toString());
                response.put("userId", existingUser.getUserId());
                response.put("message", "Login successful.");
                System.out.println(response);
            } else {
                response.put("message", "Incorrect password. Try again.");
                System.out.println(response);
            }
        } else {
            response.put("message", "Your account does not exist. Please register!");
            System.out.println(response);
        }

        return response;
    }
    
    
}
