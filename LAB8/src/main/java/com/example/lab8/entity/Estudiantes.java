package com.example.lab8.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Estudiantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 40)
    private String nombre;

    @Column(name="gpa" ,  nullable = false)
    private Double gpa;

    @Column(name="facultad" ,  nullable = false)
    private String facultad;

    @Column(name="creditos_completados" ,  nullable = false)
    private Integer creditos;

}
