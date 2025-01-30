package com.lin.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lin.entity.Subject;
import com.lin.service.SubjectService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjectsWithTopicsAndFlashcards();
    }


    @GetMapping("/search")
    public ResponseEntity<Subject> getSubjectByName(@RequestParam String name) {
        Optional<Subject> subject = subjectService.getSubjectByName(name);
        if (subject.isPresent()) {
            return ResponseEntity.ok(subject.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }




    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable String id) { 
        Optional<Subject> subject = subjectService.getSubjectById(id); 
        if (subject.isPresent()) {
            return ResponseEntity.ok(subject.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // Toggle privacy of the subject
    @PostMapping("/{id}/toggle-privacy")
    public ResponseEntity<Subject> togglePrivacy(@PathVariable String id, @RequestParam String privacy) {
        try {
            Subject updatedSubject = subjectService.togglePrivacy(id, privacy);
            return ResponseEntity.ok(updatedSubject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Add user to protected subject
    @PostMapping("/{id}/add-user")
    public ResponseEntity<Subject> addUserToProtectedSubject(@PathVariable String id, @RequestParam String userId) {
        try {
            Subject updatedSubject = subjectService.addUserToProtectedSubject(id, userId);
            return ResponseEntity.ok(updatedSubject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Remove user from protected subject
    @PostMapping("/{id}/remove-user")
    public ResponseEntity<Subject> removeUserFromProtectedSubject(@PathVariable String id, @RequestParam String userId) {
        try {
            Subject updatedSubject = subjectService.removeUserFromProtectedSubject(id, userId);
            return ResponseEntity.ok(updatedSubject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping("/user/{userId}")
    public List<Subject> getSubjectsByUserId(@PathVariable String userId) {
        System.out.println(userId);
        return subjectService.getSubjectsByUserId(userId);
    }


    @PostMapping("/{id}/toggle-favorite")
    public ResponseEntity<Subject> toggleFavorite(@PathVariable String id) {
        try {
            Subject updatedSubject = subjectService.toggleFavorite(id);
            return ResponseEntity.ok(updatedSubject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping
    public ResponseEntity<Subject> saveSubject(@RequestBody Subject subject) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.saveSubject(subject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable String id) { 
        subjectService.deleteSubject(id); 
        return ResponseEntity.noContent().build();
    }


}
