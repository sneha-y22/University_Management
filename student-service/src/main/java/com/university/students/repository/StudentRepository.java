package com.university.students.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.university.students.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Search by name (case-insensitive)
    List<Student> findByNameContainingIgnoreCase(String name);

    // Get by course
    List<Student> findByCourse(String course);
}
