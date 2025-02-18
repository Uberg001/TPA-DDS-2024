package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResolucionDTOSalida {
    private Long id;
    private String foto_path;
    private String descripcion;
    private String fecha;
    private TecnicoDTOSalida tecnico;
    private String estadoHeladera;
}
