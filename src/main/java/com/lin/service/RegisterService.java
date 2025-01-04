package com.lin.service;

import com.lin.entity.ConfirmToken;
import com.lin.entity.User;
import com.lin.entity.UserProfile;
import com.lin.repository.ConfirmationTokenRepository;
import com.lin.repository.UserProfileRepository;
import com.lin.repository.UserRepository;
import com.lin.utils.myToken;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String send(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            if (existingUser.isEnabled() == false)
                return "Do not send repeatly!";
            return "existing user";
        } else {
            user.setEnabled(false);
            String pwd = encoder.encode(user.getPassword());
            user.setPassword(pwd);
            userRepository.save(user);
            String token = new myToken().creatToken();
            ConfirmToken confirmationToken = new ConfirmToken(user.getUserId(), token);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("s12027995@stu.najah.edu");
            mailMessage.setText("Lama 3amti had a7la token\n\n" + token);
            emailSenderService.sendEmail(mailMessage);
            return token;
        }
    }

    public String registerUser(User user, String token) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            if (existingUser.isEnabled() == true)
                return "existing User";
            else {
                ConfirmToken confirmToken = confirmationTokenRepository.findByUserId(existingUser.getUserId());
                System.out.println(confirmToken);
                System.out.println(token);
                if (confirmToken.getConfirmationToken().equals(token)) {
                    existingUser.setEnabled(true);
                    existingUser.setUsername(user.getUsername());
                    userRepository.deleteByEmail(existingUser.getEmail());
                    userRepository.save(existingUser);

                    UserProfile userProfile = new UserProfile();
                    userProfile.setEmail(user.getEmail());
                    userProfile.setUsername(user.getUsername());
                    userProfileRepository.save(userProfile);
                    return "sucess";
                } else
                    return "badToken";
            }
        } else
            return "Please Send token first!";
    }
}
