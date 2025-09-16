package com.university.course.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.university.course.model.Course;
import com.university.course.service.CourseService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // ✅ GET all courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // ✅ GET course by ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ CREATE a new course
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course saved = courseService.createCourse(course);
        return ResponseEntity.ok(saved);
    }

    // ✅ DELETE course by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ ASSIGN faculty to course
    @PutMapping("/{courseId}/assign-faculty/{facultyId}")
    public ResponseEntity<Course> assignFaculty(
            @PathVariable Long courseId,
            @PathVariable Long facultyId) {
        Course updated = courseService.assignFaculty(courseId, facultyId);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // ✅ GET all courses for a specific faculty
    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<Course>> getCoursesByFaculty(@PathVariable Long facultyId) {
        List<Course> courses = courseService.getByFacultyId(facultyId);
        return courses.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(courses);
    }

    // ✅ GET course with faculty details (optional)
    @GetMapping("/{id}/with-faculty")
    public ResponseEntity<Map<String, Object>> getCourseWithFaculty(@PathVariable Long id) {
        Map<String, Object> result = courseService.getCourseWithFaculty(id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
}