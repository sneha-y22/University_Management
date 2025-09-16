package com.university.registration.service;

import com.university.registration.model.Registration;
import com.university.registration.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    private final RegistrationRepository repository;

    public RegistrationService(RegistrationRepository repository) {
        this.repository = repository;
    }

    public List<Registration> getAllRegistrations() {
        return repository.findAll();
    }

    public Optional<Registration> getRegistrationById(Long id) {
        return repository.findById(id);
    }

    public Registration createRegistration(Registration registration) {
        return repository.save(registration);
    }

    public Registration updateRegistration(Long id, Registration updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setStudentId(updated.getStudentId());
                    existing.setCourseId(updated.getCourseId());
                    existing.setSemester(updated.getSemester());
                    existing.setStatus(updated.getStatus());
                    return repository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Registration not found with id " + id));
    }

    public void deleteRegistration(Long id) {
        repository.deleteById(id);
    }

    public List<Registration> getRegistrationsByStudentId(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    public List<Registration> getRegistrationsByCourseId(Long courseId) {
        return repository.findByCourseId(courseId);
    }
}
