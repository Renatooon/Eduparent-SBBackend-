package org.eduparent.eduparent.repositorios;
import org.eduparent.eduparent.entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.eduparent.eduparent.entidades.Alumno;
import org.eduparent.eduparent.entidades.Curso;
import org.eduparent.eduparent.entidades.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;
import java.util.Optional;
@Repository

public interface findByAlumnoAndEvaluacion_Curso {

    List<Nota> findByAlumnoAndEvaluacion_Curso(Alumno alumno, Curso curso);

     Optional<Nota> findByAlumnoAndEvaluacion(Alumno alumno, Evaluacion evaluacion);
}










