package com.lin.service.Instructor;

import com.lin.entity.Instructor.Classroom;
import com.lin.entity.Instructor.ClassroomRelationship;
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



    // Get relationships by instructorId
    public List<ClassroomRelationship> getClassroomRelationshipsByInstructorId(String instructorId) {
        return classroomRelationshipRepository.findByInstructorId(instructorId);
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
}
