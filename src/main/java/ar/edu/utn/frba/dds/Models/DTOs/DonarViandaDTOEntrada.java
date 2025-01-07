package ar.edu.utn.frba.dds.Models.DTOs;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Utils.EstadoVianda;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor @Data
public class DonarViandaDTOEntrada {

    private LocalDate fechaCaducidad;
    private LocalDate fechaDonacion;
    private String comida;
    private EstadoVianda estado;
    private Heladera heladera;
    private PersonaHumanaDTOSalida colaborador;
    private Integer calorias;
    private Integer peso;

}
