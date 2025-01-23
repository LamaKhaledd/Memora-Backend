package com.lin.controller.Instructor;

import com.lin.entity.Instructor.ClassroomRelationship;
import com.lin.entity.User;
import com.lin.repository.Instructor.ClassroomRelationshipRepository;
import com.lin.service.Instructor.ClassroomRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/classroom-relationships")
public class ClassroomRelationshipController {

    @Autowired
    private ClassroomRelationshipService classroomRelationshipService;


    @Autowired
    private ClassroomRelationshipRepository classroomRelationshipRepository;


    /*
    // Get relationships by instructor userId
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<ClassroomRelationship>> getClassroomRelationshipsByInstructorId(@PathVariable String instructorId) {
        List<ClassroomRelationship> relationships = classroomRelationshipService.getClassroomRelationshipsByInstructorId(instructorId);
        if (!relationships.isEmpty()) {
            return ResponseEntity.ok(relationships);
        }
        return ResponseEntity.notFound().build();
    }
*/
    // Get relationships by classroomId
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<ClassroomRelationship>> getClassroomRelationshipsByClassroomId(@PathVariable String classroomId) {
        List<ClassroomRelationship> relationships = classroomRelationshipService.getClassroomRelationshipsByClassroomId(classroomId);
        if (!relationships.isEmpty()) {
            return ResponseEntity.ok(relationships);
        }
        return ResponseEntity.notFound().build();
    }

    // Get relationships by student userId
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ClassroomRelationship>> getClassroomRelationshipsByStudentId(@PathVariable String studentId) {
        List<ClassroomRelationship> relationships = classroomRelationshipService.getClassroomRelationshipsByStudentId(studentId);
        if (!relationships.isEmpty()) {
            return ResponseEntity.ok(relationships);
        }
        return ResponseEntity.notFound().build();
    }

    // Get relationships by student userId
    @GetMapping("/all")
    public ResponseEntity<List<ClassroomRelationship>> getAllClassroomRelationships() {
        List<ClassroomRelationship> relationships = classroomRelationshipRepository.findAll();
        if (!relationships.isEmpty()) {
            return ResponseEntity.ok(relationships);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/{classroomId}/add-student")
    public ResponseEntity<String> addStudentToClassroom(
            @PathVariable String classroomId,
            @RequestParam String instructorId,
            @RequestParam String studentId) {

        try {
            classroomRelationshipService.addStudentToClassroom(classroomId, instructorId, studentId);
            return ResponseEntity.ok("Student added to classroom successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }






    @GetMapping("/{classroomId}/students")
    public ResponseEntity<List<User>> getStudentsByClassroomId(@PathVariable String classroomId) {
        try {
            // Fetch students from the service
            List<User> students = classroomRelationshipService.getStudentsByClassroomId(classroomId);

            // Return the list of students
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }


    @DeleteMapping("/{classroomId}/students/{studentId}")
    public ResponseEntity<Void> removeStudentFromClassroom(
            @PathVariable String classroomId,
            @PathVariable String studentId) {
        try {
            classroomRelationshipService.removeStudentFromClassroom(classroomId, studentId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();  // Log the error for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
