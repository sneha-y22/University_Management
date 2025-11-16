package com.university.students.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.university.students.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Search by name (case-insensitive)
    List<Student> findByNameContainingIgnoreCase(String name);

    // Get by course
    List<Student> findByCourse(String course);

        // Already existing custom queries?
    Page<Student> findAll(Pageable pageable);

    @Query("SELECT s FROM Student s WHERE " +
        "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
        "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
        "LOWER(s.course) LIKE LOWER(CONCAT('%', :keyword, '%'))")
Page<Student> searchGlobal(@Param("keyword") String keyword, org.springframework.data.domain.Pageable pageable);

}
