package org.eduparent.eduparent.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "academica_materia")
@Getter
@Setter
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "nivel_id") // Asegúrate de que este sea el nombre correcto de la FK
    private Nivel nivel;
}
