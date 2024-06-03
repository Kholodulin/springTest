package dev.vorstuu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "password")
@Getter @Setter
public class Password {
    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    protected Password(){}
    public Password(String password){
        this.password = passwordEncoder.encode(password);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String password;

    @JsonIgnore
    private void setPasswordWithEncoding(String password){
        this.password = passwordEncoder.encode(password);
    }
}
