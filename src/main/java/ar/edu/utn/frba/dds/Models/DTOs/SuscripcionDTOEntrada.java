package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuscripcionDTOEntrada {
    private Long idHeladera;
    private Long idPersona;
    private String tipo;
    private String tipoPersona;
}
