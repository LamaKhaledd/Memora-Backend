package com.lin.controller.Instructor;

import com.lin.entity.Instructor.ClassroomRelationship;
import com.lin.service.Instructor.ClassroomRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classroom-relationships")
public class ClassroomRelationshipController {

    @Autowired
    private ClassroomRelationshipService classroomRelationshipService;



    // Get relationships by instructor userId
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<ClassroomRelationship>> getClassroomRelationshipsByInstructorId(@PathVariable String instructorId) {
        List<ClassroomRelationship> relationships = classroomRelationshipService.getClassroomRelationshipsByInstructorId(instructorId);
        if (!relationships.isEmpty()) {
            return ResponseEntity.ok(relationships);
        }
        return ResponseEntity.notFound().build();
    }

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

}
