package org.eduparent.eduparent.repositorios;

import org.eduparent.eduparent.entidades.Asistencia;
import org.eduparent.eduparent.entidades.Alumno;
import org.eduparent.eduparent.entidades.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findByAlumnoAndCurso(Alumno alumno, Curso curso);
    List<Asistencia> findByAlumno(Alumno alumno);

}
