package org.eduparent.eduparent.repositorios;

import org.eduparent.eduparent.entidades.Alumno;
import org.eduparent.eduparent.entidades.Curso;
import org.eduparent.eduparent.entidades.Evaluacion;
import org.eduparent.eduparent.entidades.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {

    // Para obtener todas las notas de un alumno en un curso específico
    List<Nota> findByAlumnoAndEvaluacion_Curso(Alumno alumno, Curso curso);

    // Si quieres obtener todas las notas de una evaluación
    List<Nota> findByEvaluacion(Evaluacion evaluacion);

    List<Nota> findByAlumno(Alumno alumno);
}
