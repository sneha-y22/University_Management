package com.university.course.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.university.course.model.Course;
import com.university.course.repository.CourseRepository;

import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final RestTemplate restTemplate;

    // Faculty Service Base URL
    private static final String FACULTY_SERVICE_URL = "http://localhost:7001/faculty/api/faculty/";

    public CourseService(CourseRepository courseRepository, RestTemplate restTemplate) {
        this.courseRepository = courseRepository;
        this.restTemplate = restTemplate;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    // Assign a faculty to a course
    public Course assignFaculty(Long courseId, Long facultyId) {
        Optional<Course> optional = courseRepository.findById(courseId);
        if (optional.isPresent()) {
            Course course = optional.get();
            course.setFacultyId(facultyId);
            return courseRepository.save(course);
        }
        return null;
    }

    // ✅ NEW: Get courses by facultyId
    public List<Course> getByFacultyId(Long facultyId) {
        return courseRepository.findByFacultyId(facultyId);
    }

    // ✅ NEW: Course with faculty details
    public Map<String, Object> getCourseWithFaculty(Long id) {
        Optional<Course> optional = courseRepository.findById(id);
        if (optional.isPresent()) {
            Course course = optional.get();

            // Call Faculty Service
            Map<String, Object> faculty =
                    restTemplate.getForObject(FACULTY_SERVICE_URL + course.getFacultyId(), Map.class);

            Map<String, Object> result = new HashMap<>();
            result.put("course", course);
            result.put("faculty", faculty);

            return result;
        }
        return null;
    }
}