package org.eduparent.eduparent.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "academica_curso")
@Getter
@Setter
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    // Curso.java
    @ManyToMany(mappedBy = "cursos")
    private List<Alumno> alumnos;

    @ManyToOne
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name = "grado_id", nullable = false)
    private Grado grado;
}
