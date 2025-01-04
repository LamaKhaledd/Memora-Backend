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


    @GetMapping("/user/{userId}")
    public List<Subject> getSubjectsByUserId(@PathVariable String userId) {
        return subjectService.getSubjectsWithTopicsAndFlashcardsByUserId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable String id) { // Change to String
        Optional<Subject> subject = subjectService.getSubjectById(id); // Update method signature to accept String
        if (subject.isPresent()) {
            return ResponseEntity.ok(subject.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Subject> saveSubject(@RequestBody Subject subject) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.saveSubject(subject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable String id) { // Change to String
        subjectService.deleteSubject(id); // Update method signature to accept String
        return ResponseEntity.noContent().build();
    }


}
