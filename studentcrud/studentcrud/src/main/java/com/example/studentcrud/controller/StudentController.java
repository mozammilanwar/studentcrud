package com.example.studentcrud.controller;

import com.example.studentcrud.model.Student;
import com.example.studentcrud.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            Student updatedStudent = student.get();
            updatedStudent.setName(studentDetails.getName());
            updatedStudent.setRoll(studentDetails.getRoll());
            updatedStudent.setAge(studentDetails.getAge());
            updatedStudent.setFatherName(studentDetails.getFatherName());
            return new ResponseEntity<>(studentService.saveStudent(updatedStudent), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
