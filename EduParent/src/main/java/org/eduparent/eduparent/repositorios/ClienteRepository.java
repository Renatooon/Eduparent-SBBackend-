package org.eduparent.eduparent.repositorios;

import java.util.Optional;
import org.eduparent.eduparent.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByDni(String dni);

    Optional<Cliente> findByDniAndContraseña(String dni, String contraseña);
}
