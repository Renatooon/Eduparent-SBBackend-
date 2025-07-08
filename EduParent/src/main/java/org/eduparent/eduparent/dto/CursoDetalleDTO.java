package org.eduparent.eduparent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoDetalleDTO {
    private String nombre;                 // nombre del curso
    private String emailProfesor;
    private String nombreProfesor;
    private double promedioNotas;
    private double porcentajeAsistencia;
    private int presentes;
    private int tardanzas;
    private int ausentes;
    private String grado;
    private String seccion;
    private List<EvaluacionConDetallesDTO> evaluaciones;
    private List<AsistenciaDetalleDTO> asistencias;
}
