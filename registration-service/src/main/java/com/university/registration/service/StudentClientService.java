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
public class StudentClientService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "http://localhost:8082/students"; // Student-service endpoint

    // ✅ Fetch all students
    public List<Map<String, Object>> getAllStudents() {
        ResponseEntity<List<Map<String, Object>>> response =
                restTemplate.exchange(BASE_URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Map<String, Object>>>() {});
        return response.getBody();
    }

    // ✅ Fetch a single student by ID
    public Map<String, Object> getStudentById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, Map.class);
    }

    // ✅ Register a new student
    public Map<String, Object> registerStudent(String name, String email, String course) {
        Map<String, Object> studentData = new HashMap<>();
        studentData.put("name", name);
        studentData.put("email", email);
        studentData.put("course", course);

        return restTemplate.postForObject(BASE_URL, studentData, Map.class);
    }
}
