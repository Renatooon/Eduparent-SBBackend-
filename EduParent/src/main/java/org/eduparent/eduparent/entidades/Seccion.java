package org.eduparent.eduparent.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "academica_seccion")
@Getter
@Setter
public class Seccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String letra;

    @ManyToOne
    @JoinColumn(name = "grado_id", nullable = false)
    private Grado grado;
}
