package com.lin.service;
import com.lin.entity.User;
import com.lin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public String loginUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            if (!existingUser.isEnabled()) {
                return "The email is not verified.";
            }
            if (encoder.matches(user.getPassword(), existingUser.getPassword())) {
                return String.format("Login successful. User role: %s", existingUser.getRole());
            } else {
                return "Incorrect password. Try again.";
            }
        } else {
            return "Your account does not exist. Please register!";
        }
    }
    
}
