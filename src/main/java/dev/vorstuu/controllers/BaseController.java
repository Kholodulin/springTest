package dev.vorstuu.controllers;

import dev.vorstuu.entity.Role;
import dev.vorstuu.entity.Student;
import dev.vorstuu.entity.User;
import dev.vorstuu.repositories.StudentRepository;
import dev.vorstuu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("api/base")
public class BaseController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("students")
    public List<Student> getAllStudents(Principal principal) {
        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority(Role.ADMIN.name()))) {
            return StreamSupport.stream(studentRepository.findAll().spliterator(), false)
                    .collect(Collectors.toList());
        } else if (authorities.contains(new SimpleGrantedAuthority(Role.STUDENT.name()))) {
            User user = userRepository.findByUsername(userDetails.getUsername());
            return studentRepository.findByUser(user);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @PostMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student createStudent(@RequestBody Student newStudent){
        return studentRepository.save(newStudent);
    }

    @PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student changeStudent(@RequestBody Student changingStudent) {
        changingStudent.setUser(studentRepository.findById(changingStudent.getId()).get().getUser());
        return studentRepository.save(changingStudent);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }

    @GetMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student with id " + id + " was not found"));
    }

    @GetMapping(value = "students/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> getStudentsByGroup(@RequestParam(value = "group") String group) {
        return studentRepository.findByGroup(group);
    }
}

