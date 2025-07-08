package org.eduparent.eduparent.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios_usuario")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String password;

    private String rol;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_staff")
    private boolean isStaff;

    @Column(name = "is_superuser")
    private boolean isSuperuser;
}
