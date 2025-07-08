package org.eduparent.eduparent.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.eduparent.eduparent.entidades.Competencia;
import org.eduparent.eduparent.entidades.Capacidad;
import org.eduparent.eduparent.entidades.Tema;

@Entity
@Table(name = "academica_nota")
@Getter
@Setter
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "evaluacion_id")
    private Evaluacion evaluacion;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "competencia_id", nullable = true)
    private Competencia competencia;

    @ManyToOne
    @JoinColumn(name = "capacidad_id", nullable = true)
    private Capacidad capacidad;

    @ManyToOne
    @JoinColumn(name = "tema_id", nullable = true)
    private Tema tema;

    @Column(nullable = false)
    private Double nota;
}
