package ru.samarina.MySpringBoot2Dbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.samarina.MySpringBoot2Dbase.entity.Subject;
import ru.samarina.MySpringBoot2Dbase.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> allSubjects() {
        List<Subject> allSubjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(allSubjects);
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<?> getSubject (@PathVariable("id") int id) {
        Subject subject = subjectService.getSubject(id);
        if (subject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Subject with id " + id + " not found.");
        }
        return ResponseEntity.ok(subject);
    }


    @PostMapping("/subjects")
    public ResponseEntity<String> saveSubject(@RequestBody Subject subject) {
        Subject savedSubject = subjectService.saveSubject(subject);
        if (savedSubject != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Subject created successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create subject");
    }

    @PutMapping("/subjects")
    public ResponseEntity<String> updateSubject(@RequestBody Subject subject) {
        Subject updatedSubject = subjectService.saveSubject(subject);
        if (updatedSubject != null) {
            return ResponseEntity.ok("Subject updated successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update subject");
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable("id") int id) {
        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.ok("Subject deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete subject");
        }
    }
}
