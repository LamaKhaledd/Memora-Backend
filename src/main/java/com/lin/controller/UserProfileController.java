package com.lin.controller;

import com.lin.entity.User;
import com.lin.entity.UserProfile;
import com.lin.repository.UserProfileRepository;
import com.lin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class UserProfileController {
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/findAll")
        public List<UserProfile> findAll(){
            List<UserProfile> profiles = userProfileRepository.findAll();
            if (profiles == null) {
                throw new RuntimeException("No user profiles found");
            }
            return profiles;
        }

    @GetMapping("/findByEmail")
    public UserProfile findProfileByEmail(@RequestParam("email") String email){
        return userProfileRepository.findByEmail(email);
    }


    @GetMapping("/findByUsernameLike")
    public List<UserProfile> findByUsernameLike(@RequestParam("username") String usernmae){
        return userProfileRepository.findAllByUsernameContains(usernmae);
    }

    @PostMapping(value="/save")
    public String save(@RequestBody UserProfile userProfile) {
        User user=userRepository.findByEmail(userProfile.getEmail());

        if(user!=null){
            user.setUsername(userProfile.getUsername());
            userRepository.deleteByEmail(user.getEmail());
            userRepository.save(user);                  

            userProfileRepository.deleteByEmail(userProfile.getEmail());
            userProfileRepository.save(userProfile);   
            return "sucess";
        }
        else
            return "User Don't Exit, Please Register First!";
    }

    @PostMapping(value="/update")
    public String update(@RequestBody UserProfile userProfile) {
        User user=userRepository.findByEmail(userProfile.getEmail());
        if(user!=null){
            user.setUsername(userProfile.getUsername());
            userRepository.deleteByEmail(user.getEmail());
            userRepository.save(user);               

            userProfileRepository.deleteByEmail(userProfile.getEmail());
            userProfileRepository.save(userProfile); 
            return "sucess";
        }
        else
            return "User Don't Exit, Please Register First!";
    }

    @GetMapping("/delete")
    public Integer deleteUserProfileByEmail(@RequestParam("email") String email){
        userRepository.deleteByEmail(email);
        return  userProfileRepository.deleteByEmail(email);
    }

}
