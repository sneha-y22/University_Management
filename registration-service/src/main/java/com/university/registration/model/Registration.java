package com.university.registration.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "registrations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;   // Better to store studentId instead of name
    private Long courseId;    // Better to store courseId instead of name

    private String semester;
    private String status;  // e.g. "ENROLLED", "PENDING"
}
