package org.eduparent.eduparent.repositorios;

import org.eduparent.eduparent.entidades.Alumno;
import org.eduparent.eduparent.entidades.Curso;
import org.eduparent.eduparent.entidades.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByGrado(Grado grado);
    // CursoRepository.java
    List<Curso> findByAlumnosContaining(Alumno alumno);

}
