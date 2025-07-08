package org.eduparent.eduparent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AsistenciaDetalleDTO {
    private int semana;
    private int presentes;
    private int ausentes;
    private int tardanzas;   // ✅ nuevo campo
    private int total;
    private String estado;
}
