package com.example.servicea.Model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "Student", indexes = {
        @Index(name = "idx_student_name", columnList = "name")
})
@Data

public class Student {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long Id;

    String name;

    int age;

    int idclass;


}
