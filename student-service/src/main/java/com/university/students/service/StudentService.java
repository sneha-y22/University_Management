package com.university.students.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import com.university.students.model.Student;
import com.university.students.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // ✅ Create student
    public Student createStudent(Student student) {
        return repository.save(student);
    }

    // ❗ Get all students (without pagination)
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // ❗ Get with pagination + sorting (NEW)
    public Page<Student> getStudentsPaged(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findAll(pageable);
    }

    // ❗ Global Search by name/course/email with pagination (NEW)
    public Page<Student> searchStudents(String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return repository.searchGlobal(keyword, pageable);
    }

    // Get student by ID
    public Student getStudentById(Long id) {
        Optional<Student> studentOpt = repository.findById(id);
        return studentOpt.orElse(null);
    }

    // Update student safely
    public Student updateStudent(Long id, Student student) {
        return repository.findById(id).map(existing -> {
            existing.setName(student.getName());
            existing.setEmail(student.getEmail());
            existing.setCourse(student.getCourse());
            return repository.save(existing);
        }).orElse(null);
    }

    // Delete student
    public boolean deleteStudent(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // search by name only (OLD)
    public List<Student> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    // Get by course
    public List<Student> getStudentsByCourse(String course) {
        return repository.findByCourse(course);
    }
}
