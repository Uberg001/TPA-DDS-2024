package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidenteDTOEntrada {
    private String descripcion;
    private String tipoIncidente;
    private LocalDateTime fecha;
    private Long Heladera;
}
