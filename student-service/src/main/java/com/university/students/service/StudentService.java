package com.university.students.service;

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

    // ✅ Get all students
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // ✅ Get student by ID (returns null if not found)
    public Student getStudentById(Long id) {
        Optional<Student> studentOpt = repository.findById(id);
        return studentOpt.orElse(null);
    }

    // ✅ Update student safely
    public Student updateStudent(Long id, Student student) {
        return repository.findById(id).map(existing -> {
            existing.setName(student.getName());
            existing.setEmail(student.getEmail());
            existing.setCourse(student.getCourse());
            return repository.save(existing);
        }).orElse(null);
    }

    // ✅ Delete student safely
    public boolean deleteStudent(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // ✅ Search students by name (case insensitive)
    public List<Student> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    // ✅ Get students by course
    public List<Student> getStudentsByCourse(String course) {
        return repository.findByCourse(course);
    }
}
