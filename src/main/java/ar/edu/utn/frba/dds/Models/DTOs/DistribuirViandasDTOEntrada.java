package ar.edu.utn.frba.dds.Models.DTOs;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistribuirViandasDTOEntrada {
    private Long heladeraOrigen;
    private Long heladeraDestino;
    private Long personaHumana;
    private Integer cantViandas;
    private String motivo;
    private LocalDate fechaRealizacion;
}
