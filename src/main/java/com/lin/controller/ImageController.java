// Class a7lam 3aser 'useless', bas it's okay don't delete it we may need it at some point

package com.lin.controller;

import com.lin.entity.UserProfile;
import com.lin.repository.UserProfileRepository;
import com.lin.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/image")
public class ImageController {


    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UploadService uploadService;

    @Value("${img.imgURL}")
    private String imgURL;

    @PostMapping(value="/update")
    public String update(@RequestParam("file") MultipartFile file,@RequestParam("email") String email) {
        UserProfile userProfile = userProfileRepository.findByEmail(email);
        if(file==null) return "empty file";
        if(userProfile!=null){
            String indexPath=userProfile.getProfileId();
            int lastIndexOf = file.getOriginalFilename().lastIndexOf(".");
            String suffix = file.getOriginalFilename().substring(lastIndexOf);
            String fileName=indexPath + suffix;
            uploadService.upload(file,fileName);
            userProfile.setPath(imgURL+fileName);
            userProfileRepository.deleteByEmail(email);
            userProfileRepository.save(userProfile);
            return "sucess";
        }
        else
            return "Email error";
    }
}
