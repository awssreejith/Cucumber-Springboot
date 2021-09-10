package com.sreejith.cucumberdemo.controller;

import com.sreejith.cucumberdemo.model.StudentVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class MyController {

    Map<String, StudentVo> students = new HashMap<>();

    @PutMapping(path = "/student",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createStudent(@RequestBody StudentVo studentVo)
    {
        String key = UUID.randomUUID().toString();
        StudentVo newStudent = new StudentVo(key,studentVo.getName(),studentVo.getCountry(),studentVo.getSubject(),studentVo.getUniversity());
        students.put(key,newStudent);
        return ResponseEntity.status(HttpStatus.OK).body("New Student created with ID: "+newStudent.getId());
    }

    @PostMapping(path="/student",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateStudent(@RequestBody StudentVo studentVo)
    {
        Optional<StudentVo> studentOpt = Optional.ofNullable(students.get(studentVo.getId()));
        if(studentOpt.isPresent())
        {
            students.remove(studentOpt.get().getId());
            students.put(studentOpt.get().getId(),studentOpt.get());
            return ResponseEntity.status(HttpStatus.OK).body("User Updated succesfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }


    @GetMapping("/student/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") String studentID)
    {
        Optional <StudentVo> retStudent = Optional.of(students.get(studentID));
        return ResponseEntity.status(HttpStatus.OK).body(retStudent.get());
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> removeStudent(@PathVariable("id") String studentID)
    {
        Optional<StudentVo> retStudent = Optional.of(students.get(studentID));
        students.remove(retStudent.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body("Student Deleted");
    }



}
