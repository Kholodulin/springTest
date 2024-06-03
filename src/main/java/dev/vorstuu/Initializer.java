package dev.vorstuu;

import dev.vorstuu.entity.Password;
import dev.vorstuu.entity.Role;
import dev.vorstuu.entity.User;
import dev.vorstuu.repositories.UserRepository;
import dev.vorstuu.repositories.StudentRepository;
import dev.vorstuu.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Initializer {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;

    public void initial(){
        User admin = new User(
                null,
                "admin",
                Role.ADMIN,
                new Password("1234"),
                true
        );
        userRepository.save(admin);

        User student1 = new User(
                null,
                "student",
                Role.STUDENT,
                new Password("1234"),
                true
        );
        Student s1 = new Student("Andrey", "group 10", "+7");
        s1.setUser(student1);
        studentRepository.save(s1);

        User student2 = new User(
                null,
                "student2",
                Role.STUDENT,
                new Password("12345"),
                true
        );
        Student s2 = new Student("Ivan", "group 20", "+8");
        s2.setUser(student2);
        studentRepository.save(s2);
        studentRepository.save(new Student("RandomStudent1", "AP 101", "+7"));
        studentRepository.save(new Student("RandomStudent2", "VM 102", "+7"));
        studentRepository.save(new Student("RandomStudent3", "AP 101", "+7"));
        studentRepository.save(new Student("RandomStudent4", "VM 102", "+7"));
        studentRepository.save(new Student("RandomStudent5", "AP 101", "+7"));
        studentRepository.save(new Student("RandomStudent6", "VM 102", "+7"));
    }
}
