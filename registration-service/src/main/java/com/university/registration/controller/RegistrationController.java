package com.university.registration.controller;

import com.university.registration.model.Registration;
import com.university.registration.service.CourseClientService;
import com.university.registration.service.FacultyClientService;
import com.university.registration.service.RegistrationService;
import com.university.registration.service.StudentClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationService service;
    private final StudentClientService studentClientService;
    private final CourseClientService courseClientService;
    private final FacultyClientService facultyClientService;

    public RegistrationController(RegistrationService service,
                                  StudentClientService studentClientService,
                                  CourseClientService courseClientService,
                                  FacultyClientService facultyClientService) {
        this.service = service;
        this.studentClientService = studentClientService;
        this.courseClientService = courseClientService;
        this.facultyClientService = facultyClientService;
    }

    // ✅ Get all registrations
    @GetMapping
    public List<Registration> getAll() {
        return service.getAllRegistrations();
    }

    // ✅ Get registration by ID
    @GetMapping("/{id}")
    public ResponseEntity<Registration> getById(@PathVariable Long id) {
        return service.getRegistrationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Create new registration
    @PostMapping
    public ResponseEntity<Registration> create(@RequestBody Registration registration) {
        registration.setStatus("ENROLLED");
        Registration saved = service.createRegistration(registration);
        return ResponseEntity.ok(saved);
    }

    // ✅ Update registration
    @PutMapping("/{id}")
    public ResponseEntity<Registration> update(@PathVariable Long id,
                                               @RequestBody Registration registration) {
        try {
            return ResponseEntity.ok(service.updateRegistration(id, registration));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Delete registration
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Get all registrations for a student
    @GetMapping("/student/{studentId}")
    public List<Registration> getByStudent(@PathVariable Long studentId) {
        return service.getRegistrationsByStudentId(studentId);
    }

    // ✅ Get all registrations for a course
    @GetMapping("/course/{courseId}")
    public List<Registration> getByCourse(@PathVariable Long courseId) {
        return service.getRegistrationsByCourseId(courseId);
    }

    // ✅ Get all students
    @GetMapping("/students")
    public List<Map<String, Object>> getAllStudents() {
        return studentClientService.getAllStudents();
    }

    // ✅ Get all courses
    @GetMapping("/courses")
    public List<Map<String, Object>> getAllCourses() {
        return courseClientService.getAllCourses();
    }

    // ✅ Get all faculties
    @GetMapping("/faculties")
    public List<Map<String, Object>> getAllFaculties() {
        return facultyClientService.getAllFaculties();
    }

    // ✅ Combine registrations with student details
    @GetMapping("/with-students")
    public List<Map<String, Object>> getRegistrationsWithStudents() {
        List<Registration> registrations = service.getAllRegistrations();
        List<Map<String, Object>> students = studentClientService.getAllStudents();

        return registrations.stream().map(reg -> {
            Map<String, Object> result = new HashMap<>();
            result.put("registration", reg);

            if (reg.getStudentId() != null) {
                students.stream()
                        .filter(stu -> {
                            Object idObj = stu.get("id");
                            if (idObj instanceof Integer intId) return intId.longValue() == reg.getStudentId();
                            else if (idObj instanceof Long longId) return longId.equals(reg.getStudentId());
                            return false;
                        })
                        .findFirst()
                        .ifPresent(stu -> result.put("student", stu));
            }

            return result;
        }).toList();
    }

    // ✅ Combine registration with student + course + faculty
    @GetMapping("/full-details")
    public List<Map<String, Object>> getFullRegistrationDetails() {
        List<Registration> registrations = service.getAllRegistrations();
        List<Map<String, Object>> students = studentClientService.getAllStudents();
        List<Map<String, Object>> courses = courseClientService.getAllCourses();
        List<Map<String, Object>> faculties = facultyClientService.getAllFaculties();

        return registrations.stream().map(reg -> {
            Map<String, Object> result = new HashMap<>();
            result.put("registration", reg);

            // Student
            students.stream()
                    .filter(stu -> {
                        Object idObj = stu.get("id");
                        if (idObj instanceof Integer intId) return intId.longValue() == reg.getStudentId();
                        else if (idObj instanceof Long longId) return longId.equals(reg.getStudentId());
                        return false;
                    })
                    .findFirst()
                    .ifPresent(stu -> result.put("student", stu));

            // Course
            courses.stream()
                    .filter(course -> {
                        Object idObj = course.get("id");
                        if (idObj instanceof Integer intId) return intId.longValue() == reg.getCourseId();
                        else if (idObj instanceof Long longId) return longId.equals(reg.getCourseId());
                        return false;
                    })
                    .findFirst()
                    .ifPresent(course -> {
                        result.put("course", course);

                        // Faculty based on course's facultyId
                        Object facultyIdObj = course.get("facultyId");
                        if (facultyIdObj != null) {
                            Long facultyId = (facultyIdObj instanceof Integer intId) ? ((Integer) facultyIdObj).longValue() : (Long) facultyIdObj;
                            faculties.stream()
                                    .filter(fac -> {
                                        Object idObj = fac.get("id");
                                        if (idObj instanceof Integer intId) return intId.longValue() == facultyId;
                                        else if (idObj instanceof Long longId) return longId.equals(facultyId);
                                        return false;
                                    })
                                    .findFirst()
                                    .ifPresent(fac -> result.put("faculty", fac));
                        }
                    });

            return result;
        }).toList();
    }
}
