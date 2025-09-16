package com.university.course.model;

import jakarta.persistence.*;

@Entity
@Table(name = "courses") // optional, but good for clarity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)  // course name cannot be null
    private String name;

    private Long facultyId; // only storing facultyId, not the full Faculty object

    public Course() {}

    public Course(String name, Long facultyId) {
        this.name = name;
        this.facultyId = facultyId;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    // No setter for ID (JPA will handle it)
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }
}