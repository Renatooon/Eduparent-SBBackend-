package org.eduparent.eduparent.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String dni;  // El padre se logea con el DNI de su hijo

    @Column(nullable = false)
    private String contraseña;

    public Cliente() {}

    public Cliente(String dni, String contraseña) {
        this.dni = dni;
        this.contraseña = contraseña;
    }

    public Long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
