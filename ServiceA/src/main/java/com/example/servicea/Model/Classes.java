package com.example.servicea.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Class")
public class Classes {
    @Id
    Long Id;
    String nameclass;

}
