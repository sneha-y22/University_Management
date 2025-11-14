package com.university.registration.dto;

import com.university.registration.model.Registration;
import java.util.Map;

public class RegistrationWithStudent {
    private Registration registration;
    private Map<String, Object> student;

    public RegistrationWithStudent(Registration registration, Map<String, Object> student) {
        this.registration = registration;
        this.student = student;
    }

    public Registration getRegistration() { return registration; }
    public void setRegistration(Registration registration) { this.registration = registration; }

    public Map<String, Object> getStudent() { return student; }
    public void setStudent(Map<String, Object> student) { this.student = student; }
}
