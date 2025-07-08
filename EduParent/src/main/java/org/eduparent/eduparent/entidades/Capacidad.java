package org.eduparent.eduparent.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "academica_capacidad")
@Getter
@Setter
public class Capacidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "competencia_id", nullable = false)
    private Competencia competencia;
}
