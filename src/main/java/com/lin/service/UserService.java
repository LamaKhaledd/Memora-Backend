package com.lin.service;

import java.util.List;

import com.lin.entity.Instructor.Classroom;
import com.lin.entity.Instructor.ClassroomRelationship;
import com.lin.service.Instructor.ClassroomRelationshipService;
import com.lin.service.Instructor.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.entity.User;
import com.lin.repository.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassroomRelationshipService classroomRelationshipService;
    @Autowired
    private ClassroomService classroomService;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    public User updateUser(String userId, String username, String about) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(username);
        user.setAbout(about);
        return userRepository.save(user);
    }


    // Fetch user by userId
    public User getUserById(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }


    // Update user information
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    // Delete a user by userId
    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }



///////////////////////////////

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



    /////////////////////////

    //Users & Classrooms


/*
    // Fetch all students for an instructor based on instructorId
    public List<User> getStudentsByInstructorId(String instructorId) {
        // Fetch all classroom relationships associated with the instructor
        List<Classroom> relationships = classroomService.getClassroomsByInstructorId(instructorId);

        // Collect all unique student IDs from the relationships
        Set<String> studentIds = relationships.stream()
                .flatMap(relationship -> relationship.getStudentIds().stream())
                .collect(Collectors.toSet());

        // Fetch student details based on student IDs
        return userRepository.findByUserIdIn(List.copyOf(studentIds));
    }
*/
    public List<User> getStudentsByClassroomId(String classroomId) {
        // Fetch classroom relationships by classroomId
        List<ClassroomRelationship> relationships = classroomRelationshipService.getClassroomRelationshipsByClassroomId(classroomId);

        if (relationships.isEmpty()) {
            throw new RuntimeException("No students found for the classroom.");
        }

        // Get the student IDs from the relationship
        List<String> studentIds = relationships.stream()
                .flatMap(relationship -> relationship.getStudentIds().stream())
                .collect(Collectors.toList());

        // Fetch student details based on student IDs
        return userRepository.findByUserIdIn(studentIds);
    }





    public List<User> findUsersByIds(List<String> userIds) {
        return userRepository.findByUserIdIn(userIds);
    }





}
