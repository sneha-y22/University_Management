package com.university.registration.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseClientService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "http://localhost:8083/courses"; // Course-service endpoint

    // ✅ Fetch all courses
    public List<Map<String, Object>> getAllCourses() {
        ResponseEntity<List<Map<String, Object>>> response =
                restTemplate.exchange(BASE_URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Map<String, Object>>>() {});
        return response.getBody();
    }

    // ✅ Fetch a single course by ID
    public Map<String, Object> getCourseById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, Map.class);
    }

    // ✅ Add a new course
    public Map<String, Object> addCourse(String name, String description, Long facultyId) {
        Map<String, Object> courseData = new HashMap<>();
        courseData.put("name", name);
        courseData.put("description", description);
        courseData.put("facultyId", facultyId);

        return restTemplate.postForObject(BASE_URL, courseData, Map.class);
    }
}
