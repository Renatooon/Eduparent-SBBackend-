package org.eduparent.eduparent.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "academica_evaluacion")
@Getter
@Setter
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;  // ejemplo: "Examen 1"

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    // Evaluacion.java
    private Double nota;

    @ManyToOne
    @JoinColumn(name = "tema_id")
    private Tema tema;

}
