package org.eduparent.eduparent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionDetalleDTO {
    private String tituloEvaluacion;
    private double nota;
 }
