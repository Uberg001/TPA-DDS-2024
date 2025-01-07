package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidenteDTOSalida {
    private Long id;
    private String fecha;
    private HeladeraDTOSalida heladera;
    private String tipoIncidente;
    private List<ResolucionDTOSalida> resolucion;
    private String descripcion;
    private String estadoIncidente;
}
