package com.lin.service.Instructor;

import com.lin.entity.Instructor.Classroom;
import com.lin.entity.Instructor.ClassroomRelationship;
import com.lin.entity.User;
import com.lin.repository.Instructor.ClassroomRelationshipRepository;
import com.lin.repository.Instructor.ClassroomRepository;
import com.lin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassroomRelationshipService {

    @Autowired
    private ClassroomRelationshipRepository classroomRelationshipRepository;


    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private UserRepository userRepository;


    public List<User> getStudentsByClassroomId(String classroomId) {
        // Step 1: Get all relationships for the classroom
        List<ClassroomRelationship> relationships = classroomRelationshipRepository.findByClassroomId(classroomId);

        // Step 2: Extract the student IDs (flattening the list of lists)
        List<String> studentIds = relationships.stream()
                .flatMap(relationship -> relationship.getStudentIds().stream()) // Flatten List<List<String>> to List<String>
                .collect(Collectors.toList());

        // Step 3: Fetch students by their IDs
        return userRepository.findByUserIdIn(studentIds);
    }



    // Get relationships by classroomId
    public List<ClassroomRelationship> getClassroomRelationshipsByClassroomId(String classroomId) {
        return classroomRelationshipRepository.findByClassroomId(classroomId);
    }

    // Get relationships by studentId
    public List<ClassroomRelationship> getClassroomRelationshipsByStudentId(String studentId) {
        return classroomRelationshipRepository.findByStudentIdsContaining(studentId);
    }

    // Get classroomIds a student belongs to
    public List<String> getClassroomIdsByStudentId(String studentId) {
        List<ClassroomRelationship> relationships = classroomRelationshipRepository.findByStudentIdsContaining(studentId);
        return relationships.stream()
                .map(ClassroomRelationship::getClassroomId)
                .collect(Collectors.toList());
    }

    public void addStudentToClassroom(String classroomId, String instructorId, String studentId) {
        // Fetch classroom details
        Classroom classroom = classroomRepository.findByClassroomId(classroomId);
        if (classroom == null) {
            throw new IllegalArgumentException("Classroom not found");
        }

        // Check if the instructor matches the classroom instructor
        if (!classroom.getInstructorId().equals(instructorId)) {
            throw new IllegalArgumentException("Only the assigned instructor can add students to this classroom.");
        }

        // Retrieve the classroom relationships
        List<ClassroomRelationship> relationships = classroomRelationshipRepository.findByClassroomId(classroomId);
        if (relationships.isEmpty()) {
            throw new IllegalArgumentException("Classroom relationship not found");
        }

        // Assuming you want to add the student to the first relationship
        ClassroomRelationship relationship = relationships.get(0);

        // Add the student if not already added
        if (!relationship.getStudentIds().contains(studentId)) {
            relationship.getStudentIds().add(studentId);
            classroomRelationshipRepository.save(relationship); // Save the updated relationship
        } else {
            throw new IllegalArgumentException("Student is already added to this classroom.");
        }
    }


    public void removeStudentFromClassroom(String classroomId, String studentId) {
        // Find the classroom by its ID
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        // Find the relationship for this classroom and student using the custom repository method
        ClassroomRelationship classroomRelationship = classroomRelationshipRepository
                .findByClassroomIdAndStudentIdsContaining(classroomId, studentId)
                .orElseThrow(() -> new RuntimeException("Student not found in this classroom"));

        // Remove the studentId from the studentIds list
        classroomRelationship.getStudentIds().remove(studentId);

        // If the list is empty, delete the relationship; otherwise, save the updated relationship
        if (classroomRelationship.getStudentIds().isEmpty()) {
            classroomRelationshipRepository.delete(classroomRelationship);
        } else {
            classroomRelationshipRepository.save(classroomRelationship);
        }

        // Optionally update the classroom's current students count
        classroom.setCurrentStudents(classroom.getCurrentStudents() - 1);
        classroomRepository.save(classroom);
    }



}
