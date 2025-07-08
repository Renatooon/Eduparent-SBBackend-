package org.eduparent.eduparent.servicios;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.eduparent.eduparent.dto.AsistenciaDetalleDTO;
import org.eduparent.eduparent.dto.CursoDetalleDTO;
import org.eduparent.eduparent.dto.EvaluacionConDetallesDTO;
import org.eduparent.eduparent.entidades.*;
import org.eduparent.eduparent.repositorios.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumnoServicio {

    private final AlumnoRepository     alumnoRepository;
    private final CursoRepository      cursoRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final NotaRepository       notaRepository;
    private final AsistenciaRepository asistenciaRepository;
    private final EmailServicio        emailServicio;

    /* ============================================================= */
    /* ===================== REGISTRO / LOGIN PADRE ================= */
    /* ============================================================= */
    @Transactional
    public Alumno registrarAlumno(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    @Transactional
    public void vincularCuentaPadre(String dni, String correoPadre, String contraseña) {
        Alumno alumno = alumnoRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        alumno.setCorreoPadre(correoPadre);
        alumno.setContraseña(contraseña);
        alumnoRepository.save(alumno);
    }

    public Optional<Alumno> validarLoginPadre(String dni, String contraseña) {
        return alumnoRepository.findByDni(dni)
                .filter(a -> a.getCorreoPadre() != null
                        && a.getContraseña() != null
                        && a.getContraseña().equals(contraseña));
    }

    /* ============================================================= */
    /* ================== DETALLE CURSOS / NOTAS / ASIST. ========== */
    /* ============================================================= */
    public Map<String, Object> obtenerCursosDetallePorAlumno(String dni) {

        /* 1. Alumno y contexto general */
        Alumno alumno = alumnoRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Grado   grado   = alumno.getGrado();
        Seccion seccion = alumno.getSeccion();

        List<CursoDetalleDTO> listaCursosDTO = new ArrayList<>();

        /* 2. Recorrer cada curso del grado */
        for (Curso curso : cursoRepository.findByGrado(grado)) {

            /* 2.1 Todas las notas del alumno en este curso ------------- */
            List<Nota> notasAlumnoCurso = notaRepository.findByAlumnoAndEvaluacion_Curso(alumno, curso);

            List<EvaluacionConDetallesDTO> listaEvaluacionesDTO = notasAlumnoCurso.stream()
                    .map(nota -> {
                        Evaluacion eval = nota.getEvaluacion();
                        String notaLetra = convertirNotaALetra(nota.getNota());
                        return new EvaluacionConDetallesDTO(
                                eval != null ? eval.getTitulo() : "Sin título",
                                nota.getNota(),
                                nota.getCompetencia() != null ? nota.getCompetencia().getNombre() : "—",
                                nota.getCapacidad()   != null ? nota.getCapacidad().getNombre()   : "—",
                                nota.getTema()        != null ? nota.getTema().getNombre()        : "—",
                                notaLetra
                        );
                    })
                    .collect(Collectors.toList());

            double sumaNotas       = listaEvaluacionesDTO.stream().mapToDouble(EvaluacionConDetallesDTO::getNota).sum();
            int    totalEvaluacion = listaEvaluacionesDTO.size();
            double promedioNotas   = totalEvaluacion > 0 ? sumaNotas / totalEvaluacion : 0.0;

            /* 2.2 Asistencias ------------------------------------------ */
            List<Asistencia> asistenciasAlumnoCurso = asistenciaRepository.findByAlumnoAndCurso(alumno, curso);

            long totalAsistencias = asistenciasAlumnoCurso.size();
            long presentes = asistenciasAlumnoCurso.stream()
                    .filter(a -> "Presente".equalsIgnoreCase(a.getEstado()))
                    .count();
            long ausentes = asistenciasAlumnoCurso.stream()
                    .filter(a -> "Ausente".equalsIgnoreCase(a.getEstado()) || "Falta".equalsIgnoreCase(a.getEstado()))
                    .count();
            long tardanzas = asistenciasAlumnoCurso.stream()
                    .filter(a -> "Tardanza".equalsIgnoreCase(a.getEstado()))
                    .count();

            double porcentajeAsistencia = totalAsistencias > 0
                    ? ((presentes + tardanzas * 0.66) / totalAsistencias) * 100
                    : 0.0;

            /* 2.3 Detalle asistencias DTO ----------------------------- */
            List<AsistenciaDetalleDTO> listaAsistenciasDTO = asistenciasAlumnoCurso.stream()
                    .map(a -> AsistenciaDetalleDTO.builder()
                            .semana(a.getSemana())
                            .estado(a.getEstado())
                            .presentes("Presente".equalsIgnoreCase(a.getEstado()) ? 1 : 0)
                            .ausentes(("Ausente".equalsIgnoreCase(a.getEstado()) ||
                                    "Falta".equalsIgnoreCase(a.getEstado())) ? 1 : 0)
                            .tardanzas("Tardanza".equalsIgnoreCase(a.getEstado()) ? 1 : 0)
                            .total(1)
                            .build())
                    .collect(Collectors.toList());

            /* 2.4 Construir DTO de curso ------------------------------ */
            Profesor profesor = curso.getProfesor();

            CursoDetalleDTO cursoDTO = CursoDetalleDTO.builder()
                    .nombre(curso.getNombre())
                    .emailProfesor(
                            profesor != null && profesor.getUsuario() != null
                                    ? profesor.getUsuario().getEmail()
                                    : "—"
                    )
                    .nombreProfesor(
                            profesor != null && profesor.getUsuario() != null
                                    ? profesor.getUsuario().getNombre() + " " + profesor.getUsuario().getApellido()
                                    : "—"
                    )
                    .promedioNotas(promedioNotas)
                    .porcentajeAsistencia(porcentajeAsistencia)
                    .presentes((int) presentes)
                    .tardanzas((int) tardanzas)
                    .ausentes((int) ausentes)
                    .grado(grado.getNombre())
                    .seccion(seccion != null ? seccion.getLetra() : "—")
                    .evaluaciones(listaEvaluacionesDTO)
                    .asistencias(listaAsistenciasDTO)
                    .build();

            listaCursosDTO.add(cursoDTO);

            /* 2.5 Alertas por correo (si aplica) ---------------------- */
            boolean bajoRendimiento = listaEvaluacionesDTO.stream().anyMatch(e -> e.getNota() < 10);
            boolean faltasAltas     = (100 - porcentajeAsistencia) >= 40;

            if ((bajoRendimiento || faltasAltas)
                    && alumno.getCorreoPadre() != null && !alumno.getCorreoPadre().isEmpty()) {

                StringBuilder msg = new StringBuilder()
                        .append("Estimado padre de familia,\n\n")
                        .append("Le informamos que su hijo/a ")
                        .append(alumno.getNombre()).append(" ").append(alumno.getApellido())
                        .append(" presenta algunas alertas en el curso \"").append(curso.getNombre()).append("\":\n\n");

                if (bajoRendimiento) {
                    msg.append("📉 Bajo rendimiento académico:\n");
                    listaEvaluacionesDTO.stream()
                            .filter(e -> e.getNota() < 10)
                            .forEach(e -> msg.append("  - Evaluación: ").append(e.getTituloEvaluacion())
                                    .append(" | Tema: ").append(e.getTema())
                                    .append(" | Nota: ").append(convertirNotaALetra(e.getNota())).append("\n"));
                    msg.append("\n");
                }

                if (faltasAltas) {
                    msg.append("🚨 Alto porcentaje de inasistencias:\n");
                    listaAsistenciasDTO.stream()
                            .filter(a -> a.getAusentes() == 1)
                            .forEach(a -> msg.append("  - Semana ").append(a.getSemana())
                                    .append(" | Estado: ").append(a.getEstado()).append("\n"));
                    msg.append("\n");
                }

                msg.append("Le recomendamos revisar el desempeño académico y comunicarse con el docente si es necesario.\n\n")
                        .append("Atentamente,\nSistema de Gestión Académica");

                emailServicio.enviarCorreo(
                        alumno.getCorreoPadre(),
                        "🔔 Alerta de Rendimiento o Asistencia - " + curso.getNombre(),
                        msg.toString()
                );
            }

        }

        /* 3. Construir respuesta general */
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("grado",   grado.getNombre());
        resultado.put("seccion", seccion != null ? seccion.getLetra() : "—");
        resultado.put("cursos",  listaCursosDTO);
        return resultado;
    }

    /* ============================================================= */
    /* ====================  UTILIDAD ============================== */
    /* ============================================================= */
    private String convertirNotaALetra(double nota) {
        if (nota >= 18) return "AD";
        if (nota >= 14) return "A";
        if (nota >= 11) return "B";
        return "C";
    }
}
