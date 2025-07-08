package org.eduparent.eduparent.repositorios;

import org.eduparent.eduparent.entidades.Evaluacion;
import org.eduparent.eduparent.entidades.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    List<Evaluacion> findByCurso(Curso curso);
}
