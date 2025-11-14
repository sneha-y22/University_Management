Team Members:

1. Samya Nagpal (22CSU154)
2. Sneha Yadav (22CSU167)
3. Somya Jain (22CSU168)
4. Srishti Bhatia (22CSU171)

Everything is running without error.

---

1. Student Management Service — Sneha Yadav

Base: /students
Endpoints:

POST /students — Register a student

GET /students — Get all students

GET /students/{id} — Get student details

PUT /students/{id} — Update student info

DELETE /students/{id} — Delete student

GET /students/search?name=John — Search by name

2. Faculty Management Service — Srishti Bhatia

Base: /faculty
Endpoints:

POST /faculty — Register new faculty

GET /faculty — Get all faculty

GET /faculty/{id} — Get faculty details

PUT /faculty/{id} — Update faculty info

DELETE /faculty/{id} — Delete faculty

GET /faculty/search?name=John — Search by name

3. Course Management Service — Somya Jain

Base: /courses
Endpoints:

POST /courses — Register new course

GET /courses — Get all courses

GET /courses/{id} — Get course details

PUT /courses/{id} — Update course

DELETE /courses/{id} — Delete course

GET /courses/search?name=Java — Search by name

4. Registration Service — Samya Nagpal

Base: /registrations
Endpoints:

POST /registrations — Register student for a course

GET /registrations — List all registrations

GET /registrations/{id} — Get specific registration

PUT /registrations/{id} — Update registration

DELETE /registrations/{id} — Cancel registration

---

1. Api Gateway - Srishti Bhatia and Samya Nagpal

2. Service Discovery - Sneha Yadav

3. Configuration Server - Somya Jain
