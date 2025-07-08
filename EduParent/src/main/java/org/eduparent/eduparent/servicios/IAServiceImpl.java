package org.eduparent.eduparent.servicios;

import org.eduparent.eduparent.entidades.*;
import org.eduparent.eduparent.repositorios.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Servicio que consulta la API de OpenAI para responder preguntas de los padres
 * usando las notas y asistencias reales del alumno.
 */
@Service
public class IAServiceImpl implements IAService {

    private final AlumnoRepository      alumnoRepo;
    private final CursoRepository       cursoRepo;
    private final NotaRepository        notaRepo;
    private final AsistenciaRepository  asistenciaRepo;   // ⬅️ NUEVO

    @Value("${openai.api.key}")
    private String apiKey;

    public IAServiceImpl(AlumnoRepository alumnoRepo,
                         CursoRepository  cursoRepo,
                         NotaRepository   notaRepo,
                         AsistenciaRepository asistenciaRepo) {   // ⬅️ Constructor con repo de asistencias
        this.alumnoRepo      = alumnoRepo;
        this.cursoRepo       = cursoRepo;
        this.notaRepo        = notaRepo;
        this.asistenciaRepo  = asistenciaRepo;
    }

    @Override
    public String procesarPreguntaDelPadre(String dniAlumno, String pregunta) {

        /* ------------------------------------------------------------------ */
        /* 1. Obtener alumno                                                  */
        /* ------------------------------------------------------------------ */
        Alumno alumno = alumnoRepo.findByDni(dniAlumno).orElse(null);

        if (alumno == null) {
            return "No se encontró al alumno con DNI " + dniAlumno;
        }

        /* ------------------------------------------------------------------ */
        /* 2. Cursos, Notas y Asistencias                                     */
        /* ------------------------------------------------------------------ */
        List<Curso> cursos = cursoRepo.findByGrado(alumno.getGrado());
        List<Nota>  notas  = notaRepo.findByAlumno(alumno);            // (asegúrate de tener este método)
        List<Asistencia> asistencias = asistenciaRepo.findByAlumno(alumno);

        long totalFaltas    = asistencias.stream()
                .filter(a -> a.getEstado().equalsIgnoreCase("falta") ||
                        a.getEstado().equalsIgnoreCase("ausente"))
                .count();

        long totalPresentes = asistencias.stream()
                .filter(a -> a.getEstado().equalsIgnoreCase("presente"))
                .count();

        long totalTardanzas = asistencias.stream()
                .filter(a -> a.getEstado().equalsIgnoreCase("tardanza"))
                .count();

        /* ------------------------------------------------------------------ */
        /* 3. Construir prompt para la IA                                     */
        /* ------------------------------------------------------------------ */
        StringBuilder prompt = new StringBuilder();
        prompt.append("Eres un asistente escolar que responde preguntas sobre el rendimiento de un alumno.\n\n")
                .append("Alumno: ").append(alumno.getNombre()).append(" ").append(alumno.getApellido()).append("\n")
                .append("Grado: ").append(alumno.getGrado().getNombre()).append("\n\n")
                .append("Resumen de asistencias:\n")
                .append("✅ Presentes: ").append(totalPresentes).append("\n")
                .append("⚠️ Tardanzas: ").append(totalTardanzas).append("\n")
                .append("❌ Faltas/Ausentes: ").append(totalFaltas).append("\n\n")
                .append("Resumen de rendimiento académico:\n");

        for (Curso curso : cursos) {
            prompt.append("📘 Curso: ").append(curso.getNombre()).append("\n");

            notas.stream()
                    .filter(n -> n.getCurso().equals(curso))
                    .forEach(n -> {
                        Evaluacion eval  = n.getEvaluacion();
                        String tituloEval = (eval != null) ? eval.getTitulo() : "Evaluación no registrada";

                        String letra      = convertirNotaALetra(n.getNota());
                        String competencia= n.getCompetencia() != null ? n.getCompetencia().getNombre() : "—";
                        String capacidad  = n.getCapacidad()   != null ? n.getCapacidad().getNombre()   : "—";
                        String tema       = n.getTema()        != null ? n.getTema().getNombre()        : "—";

                        prompt.append("  • Evaluación: ").append(tituloEval).append("\n")
                                .append("    ◦ Nota: ").append(letra).append("\n")

                                .append("    ◦ Competencia: ").append(competencia).append("\n")
                                .append("    ◦ Capacidad: ").append(capacidad).append("\n")
                                .append("    ◦ Tema: ").append(tema).append("\n");
                    });

            prompt.append("\n");
        }

        prompt.append("Pregunta del padre:\n").append(pregunta);

        /* ------------------------------------------------------------------ */
        /* 4. Llamar a OpenAI                                                 */
        /* ------------------------------------------------------------------ */
        return consultarOpenAI(prompt.toString());
    }

    /* ====================================================================== */
    /* ============================   AUXILIARES  =========================== */
    /* ====================================================================== */

    /** Convierte la nota numérica a letra según tu escala. */
    private String convertirNotaALetra(double nota) {
        if (nota >= 18) return "AD";
        if (nota >= 14) return "A";
        if (nota >= 11) return "B";
        return "C";
    }

    /** Consulta la API de OpenAI y devuelve la respuesta en texto plano. */
    private String consultarOpenAI(String prompt) {
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo"); // Cambia a "gpt-4o" o "gpt-4" si tu plan lo permite
        body.put("messages", List.of(
                Map.of("role", "system",
                        "content", "Responde como un asesor académico profesional. Sé concreto y usa los datos provistos."),
                Map.of("role", "user", "content", prompt)
        ));

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, Map.class);
            Map choices  = (Map) ((List) response.getBody().get("choices")).get(0);
            Map message  = (Map) choices.get("message");
            return message.get("content").toString().trim();
        } catch (Exception e) {
            return "Ocurrió un error al consultar la IA: " + e.getMessage();
        }

    }

}
