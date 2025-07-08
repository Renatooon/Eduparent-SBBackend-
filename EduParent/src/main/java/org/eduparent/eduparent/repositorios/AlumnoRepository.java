package org.eduparent.eduparent.repositorios;

import org.eduparent.eduparent.entidades.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Optional<Alumno> findByDni(String dni);

}
