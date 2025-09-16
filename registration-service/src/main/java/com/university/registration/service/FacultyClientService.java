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
public class FacultyClientService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "http://localhost:7002/faculties"; // Faculty-service endpoint

    // ✅ Fetch all faculties
    public List<Map<String, Object>> getAllFaculties() {
        ResponseEntity<List<Map<String, Object>>> response =
                restTemplate.exchange(BASE_URL, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Map<String, Object>>>() {});
        return response.getBody();
    }

    // ✅ Fetch a single faculty by ID
    public Map<String, Object> getFacultyById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, Map.class);
    }

    // ✅ Add a new faculty
    public Map<String, Object> addFaculty(String name, String department) {
        Map<String, Object> facultyData = new HashMap<>();
        facultyData.put("name", name);
        facultyData.put("department", department);

        return restTemplate.postForObject(BASE_URL, facultyData, Map.class);
    }
}
