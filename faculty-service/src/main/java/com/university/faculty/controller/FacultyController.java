package com.university.faculty.controller;

import com.university.faculty.model.Faculty;
import com.university.faculty.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/faculty") // Base path for all endpoints
public class FacultyController {

    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    // POST /api/faculty — Register a new faculty
    @PostMapping
    public ResponseEntity<Faculty> create(@RequestBody Faculty faculty) {
        Faculty saved = service.create(faculty);
        return ResponseEntity.created(URI.create("/api/faculty/" + saved.getId())).body(saved);
    }

    // GET /api/faculty — Get list of all faculty
    @GetMapping
    public ResponseEntity<List<Faculty>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // GET /api/faculty/{id} — Get faculty details by ID
    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getById(@PathVariable Long id) {
        Faculty f = service.getById(id);
        return f != null ? ResponseEntity.ok(f) : ResponseEntity.notFound().build();
    }

    // PUT /api/faculty/{id} — Update faculty information
    @PutMapping("/{id}")
    public ResponseEntity<Faculty> update(@PathVariable Long id, @RequestBody Faculty faculty) {
        Faculty updated = service.update(id, faculty);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // DELETE /api/faculty/{id} — Remove faculty
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // GET /api/faculty/search?name=John — Search faculty by name
    @GetMapping("/search")
    public ResponseEntity<List<Faculty>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchByName(name));
    }
}