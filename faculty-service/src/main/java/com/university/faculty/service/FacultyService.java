package com.university.faculty.service;

import com.university.faculty.model.Faculty;
import com.university.faculty.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    // Create a new faculty
    public Faculty create(Faculty faculty) {
        return repository.save(faculty);
    }

    // Get all faculty members
    public List<Faculty> getAll() {
        return repository.findAll();
    }

    // Get a faculty by ID
    public Faculty getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Update faculty details
    public Faculty update(Long id, Faculty updated) {
        return repository.findById(id)
                .map(f -> {
                    f.setName(updated.getName());
                    f.setEmail(updated.getEmail());
                    f.setDepartment(updated.getDepartment());
                    return repository.save(f);
                }).orElse(null);
    }

    // Delete a faculty
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Search faculty by name
    public List<Faculty> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
