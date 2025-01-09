package com.lin.service.Instructor;

import com.lin.entity.Instructor.Classroom;
import com.lin.entity.Instructor.ClassroomRelationship;
import com.lin.repository.Instructor.ClassroomRelationshipRepository;
import com.lin.repository.Instructor.ClassroomRepository;
import com.lin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClassroomService {
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private ClassroomRelationshipRepository classroomRelationshipRepository;

    @Autowired
    private UserRepository userRepository;


    public Optional<Classroom> getClassroomById(String classroomId) {
        return classroomRepository.findById(classroomId); // Assuming you're using a JPA repository
    }

    // Get all classrooms taught by an instructor using their userId
    public List<Classroom> getClassroomsByInstructorId(String instructorId) {
        // Fetch all classroom relationships by instructorId
        List<ClassroomRelationship> relationships = classroomRelationshipRepository.findByInstructorId(instructorId);

        // Collect the classroomIds from the relationships
        List<String> classroomIds = relationships.stream()
                .map(ClassroomRelationship::getClassroomId)
                .collect(Collectors.toList());

        // Fetch all classrooms based on classroomIds
        Iterable<Classroom> classroomsIterable = classroomRepository.findAllById(classroomIds);

        // Convert Iterable to List
        List<Classroom> classrooms = new ArrayList<>();
        classroomsIterable.forEach(classrooms::add);

        return classrooms;
    }


    // Get all classrooms a student belongs to using their userId
    public List<Classroom> getClassroomsByStudentId(String studentId) {
        // Fetch all classroom relationships where the student is enrolled
        List<ClassroomRelationship> relationships = classroomRelationshipRepository.findByStudentIdsContaining(studentId);

        // Collect the classroom IDs from the relationships
        List<String> classroomIds = relationships.stream()
                .map(ClassroomRelationship::getClassroomId)
                .collect(Collectors.toList());

        // Fetch the classrooms associated with the collected classroom IDs from the classroom repository
        Iterable<Classroom> classroomsIterable = classroomRepository.findAllById(classroomIds);

        // Convert Iterable to List using Stream API
        List<Classroom> classrooms = StreamSupport.stream(classroomsIterable.spliterator(), false)
                .collect(Collectors.toList());

        return classrooms;
    }
}
