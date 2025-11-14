package com.university.faculty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.faculty.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    // Keep only valid queries that match actual fields in Faculty
    List<Faculty> findByNameContainingIgnoreCase(String name);
}