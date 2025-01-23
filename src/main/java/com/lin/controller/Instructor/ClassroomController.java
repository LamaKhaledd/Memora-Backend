package com.lin.controller.Instructor;

import com.lin.entity.Instructor.Classroom;
import com.lin.service.Instructor.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;



    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public ResponseEntity<List<Classroom>> getAllClassrooms() {
        try {
            List<Classroom> classrooms = classroomService.getAllClassrooms();
            return ResponseEntity.ok(classrooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    // Get classroom by classroomId
    @GetMapping("/{classroomId}")
    public ResponseEntity<Classroom> getClassroomById(@PathVariable String classroomId) {
        return classroomService.getClassroomById(classroomId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Correctly mapped endpoint to fetch classrooms by instructorId
    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Classroom>> getClassroomsByInstructorId(@PathVariable String instructorId) {
        List<Classroom> classrooms = classroomService.getClassroomsByInstructorId(instructorId);
        if (!classrooms.isEmpty()) {
            return ResponseEntity.ok(classrooms);
        }
        return ResponseEntity.notFound().build();
    }


    // Get all classrooms for a student by their userId
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Classroom>> getClassroomsByStudentId(@PathVariable String studentId) {
        List<Classroom> classrooms = classroomService.getClassroomsByStudentId(studentId);
        if (!classrooms.isEmpty()) {
            return ResponseEntity.ok(classrooms);
        }
        return ResponseEntity.notFound().build();
    }



}
