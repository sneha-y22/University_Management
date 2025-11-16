package com.university.course.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.university.course.model.Course;
import com.university.course.repository.CourseRepository;
import com.university.course.dto.PageResponse;

import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final RestTemplate restTemplate;

    // Faculty Service Base URL
    private static final String FACULTY_SERVICE_URL = "http://localhost:7001/faculty/api/faculty/";

    @Value("${com.university.pagination.size}")
    private int defaultPageSize;

    public CourseService(CourseRepository courseRepository, RestTemplate restTemplate) {
        this.courseRepository = courseRepository;
        this.restTemplate = restTemplate;
    }

    // ✅ Get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // ✅ Get course by ID
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    // ✅ Create a new course
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    // ✅ Delete course by ID
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    // ✅ Assign a faculty to a course
    public Course assignFaculty(Long courseId, Long facultyId) {
        Optional<Course> optional = courseRepository.findById(courseId);
        if (optional.isPresent()) {
            Course course = optional.get();
            course.setFacultyId(facultyId);
            return courseRepository.save(course);
        }
        return null;
    }

    // ✅ Get courses by facultyId
    public List<Course> getByFacultyId(Long facultyId) {
        return courseRepository.findByFacultyId(facultyId);
    }

    // ✅ Get course with faculty details
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

    // ✅ NEW: Get courses with pagination
    public PageResponse<Course> getCourses(int page, Integer size, Integer start, Integer end) {
        int pageSize = (size != null) ? size : defaultPageSize;

        // DB-level pagination
        Page<Course> coursePage = courseRepository.findAll(PageRequest.of(page, pageSize));
        List<Course> courses = coursePage.getContent();

        // Optional slicing with start/end
        if (start != null && end != null) {
            start = Math.max(0, start);
            end = Math.min(courses.size(), end);
            courses = courses.subList(start, end);
        }

        return new PageResponse<>(
                page,
                courses.size(),
                coursePage.getTotalPages(),
                coursePage.getTotalElements(),
                courses
        );
    }
}
