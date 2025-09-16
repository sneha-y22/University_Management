package com.university.students.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class RegistrationClientService {

    private final RestTemplate restTemplate;

    public RegistrationClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getRegistrationsByStudentId(Long studentId) {
        String url = "http://localhost:7001/registrations/students/" + studentId;
        Map<String, Object>[] response = restTemplate.getForObject(url, Map[].class);
        return Arrays.asList(response);
    }
}
