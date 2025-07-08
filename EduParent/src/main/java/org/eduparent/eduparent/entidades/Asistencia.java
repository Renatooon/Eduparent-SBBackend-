package org.eduparent.eduparent.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "academica_asistencia")
@Getter
@Setter
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    private Integer semana;

    @Column(nullable = false)
    private String estado;  // "Presente" o "Falta"

}
