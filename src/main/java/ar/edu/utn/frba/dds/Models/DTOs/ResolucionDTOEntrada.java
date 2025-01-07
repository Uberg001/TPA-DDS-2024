package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResolucionDTOEntrada {
    private Long idIncidente;
    private String descripcion;
    private String foto_path;
    private String estadoHeladera;
    private Long idTecnico;
}
