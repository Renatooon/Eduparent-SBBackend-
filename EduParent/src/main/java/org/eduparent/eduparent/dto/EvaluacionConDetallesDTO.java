package org.eduparent.eduparent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EvaluacionConDetallesDTO {
    private String tituloEvaluacion;
    private double nota;
    private String competencia;
    private String capacidad;
    private String tema;
    private String notaLetra;

}


