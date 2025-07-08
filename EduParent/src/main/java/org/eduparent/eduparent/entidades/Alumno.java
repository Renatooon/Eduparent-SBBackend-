package org.eduparent.eduparent.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "academica_alumno")
@Getter
@Setter
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String dni;

    @ManyToOne
    @JoinColumn(name = "grado_id", nullable = false)
    private Grado grado;

    @ManyToMany
    @JoinTable(
            name = "academica_alumno_curso",
            joinColumns = @JoinColumn(name = "alumno_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursos;


    @ManyToOne
    @JoinColumn(name = "seccion_id", nullable = true) // se puede dejar nullable por si aún no está asignado
    private Seccion seccion;

    private String correoPadre;
    private String contraseña;
}
