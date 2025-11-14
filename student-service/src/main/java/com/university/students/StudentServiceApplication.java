package com.university.students;

import com.university.students.model.Student;
import com.university.students.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class StudentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(StudentService studentService) {
        return args -> {
            Scanner scanner = new Scanner(System.in);
            int choice = 0;

            while (choice != 3) {
                System.out.println("\n=== Student Management ===");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");

                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input! Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        // Add Student
                        System.out.println("\n=== Enter New Student Details ===");

                        System.out.print("Name: ");
                        String name = scanner.nextLine();

                        System.out.print("Email: ");
                        String email = scanner.nextLine();

                        System.out.print("Course: ");
                        String course = scanner.nextLine();

                        Student student = new Student();
                        student.setName(name);
                        student.setEmail(email);
                        student.setCourse(course);

                        studentService.createStudent(student);
                        System.out.println("✅ Student saved successfully in DB!");
                        break;

                    case 2:
                        // View All Students
                        List<Student> students = studentService.getAllStudents();
                        if (students.isEmpty()) {
                            System.out.println("No students found.");
                        } else {
                            System.out.println("\n=== All Students ===");
                            for (Student s : students) {
                                System.out.printf("ID: %d, Name: %s, Email: %s, Course: %s%n",
                                        s.getId(), s.getName(), s.getEmail(), s.getCourse());
                            }
                        }
                        break;

                    case 3:
                        System.out.println("Exiting... Goodbye!");
                        break;

                    default:
                        System.out.println("❌ Invalid choice! Please try again.");
                        break;
                }
            }

            scanner.close();
        };
    }
}
