package org.eduparent.eduparent.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "academica_grado")
@Getter
@Setter
public class Grado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;  // ejemplo: "1ero"
    private String seccion; // ejemplo: "A" o "B"

}
