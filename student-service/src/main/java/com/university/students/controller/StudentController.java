package com.university.students.controller;

import com.university.students.service.StudentService;
import com.university.students.model.Student;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // Create new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student saved = service.createStudent(student);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Get all students (WITHOUT pagination)
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(service.getAllStudents());
    }

    // NEW: Get with pagination + sorting
    @GetMapping("/paged")
    public ResponseEntity<Page<Student>> getStudentsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(service.getStudentsPaged(page, size, sortBy, sortDir));
    }

    // NEW: Global search with pagination
    @GetMapping("/filter")
    public ResponseEntity<Page<Student>> filterStudents(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.searchStudents(keyword, page, size));
    }

    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            Student student = service.getStudentById(id);
            if (student == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("❌ Student with ID " + id + " not found");
            }
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("⚠️ Error fetching student: " + e.getMessage());
        }
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            Student updated = service.updateStudent(id, student);
            if (updated == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("❌ Student with ID " + id + " not found for update");
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("⚠️ Error updating student: " + e.getMessage());
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        boolean deleted = service.deleteStudent(id);
        if (deleted) {
            return ResponseEntity.ok("✅ Student deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Student not found");
        }
    }

    // Search by name only
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchByName(name));
    }

    // Get by course
    @GetMapping("/course/{course}")
    public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable String course) {
        return ResponseEntity.ok(service.getStudentsByCourse(course));
    }
}
