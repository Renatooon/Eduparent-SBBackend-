package org.eduparent.eduparent.controladores;

import lombok.RequiredArgsConstructor;
import org.eduparent.eduparent.entidades.Alumno;
import org.eduparent.eduparent.servicios.AlumnoServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/alumnos")
//@CrossOrigin(origins = {"http://localhost:5173"})
@RequiredArgsConstructor
public class AlumnoControlador {

    private final AlumnoServicio alumnoServicio;
    @PutMapping("/vincular-padre")
    public ResponseEntity<?> vincularPadre(@RequestBody Map<String, String> datos) {
        String dni = datos.get("dni");
        String correoPadre = datos.get("correoPadre");
        String contraseña = datos.get("contraseña");

        alumnoServicio.vincularCuentaPadre(dni, correoPadre, contraseña);

        return ResponseEntity.ok(Map.of("message", "Cuenta vinculada correctamente"));
    }

    @GetMapping("/{dni}/cursos-detalle")
    public ResponseEntity<Map<String, Object>> obtenerCursosDetallePorAlumno(@PathVariable String dni) {
        Map<String, Object> resultado = alumnoServicio.obtenerCursosDetallePorAlumno(dni);
        return ResponseEntity.ok(resultado);
    }
    @PutMapping("/{dni}/vincular-cuenta-padre")
    public ResponseEntity<String> vincularCuentaPadre(@PathVariable String dni, @RequestBody Map<String, String> datos) {
        String correoPadre = datos.get("correoPadre");
        String contraseña = datos.get("contraseña");

        alumnoServicio.vincularCuentaPadre(dni, correoPadre, contraseña);

        return ResponseEntity.ok("Cuenta de padre vinculada correctamente.");
    }
    @PostMapping("/login-padre")
    public ResponseEntity<?> loginPadre(@RequestBody Map<String, String> datos) {
        String dni = datos.get("dni");
        String contraseña = datos.get("contraseña");

        Optional<Alumno> alumnoOpt = alumnoServicio.validarLoginPadre(dni, contraseña);

        return alumnoOpt.isPresent() ?
                ResponseEntity.ok(Map.of("dni", alumnoOpt.get().getDni())) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Credenciales inválidas o cuenta no vinculada"));
    }

    // 🚀 AGREGADO: Registrar alumno
    @PostMapping("/registrar")
    public ResponseEntity<Alumno> registrarAlumno(@RequestBody Alumno alumno) {
        Alumno alumnoGuardado = alumnoServicio.registrarAlumno(alumno);
        return ResponseEntity.ok(alumnoGuardado);
    }

}
