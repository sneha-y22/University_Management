package com.university.course.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.university.course.model.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByNameContainingIgnoreCase(String name);

    // ✅ Add this for faculty filter
    List<Course> findByFacultyId(Long facultyId);

    Page<Course> findAll(Pageable pageable);
}