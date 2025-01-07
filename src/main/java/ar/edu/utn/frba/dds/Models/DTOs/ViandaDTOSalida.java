package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.Data;

@Data
public class ViandaDTOSalida {
    private Long id;
    private String comida;
    private Long idColaborador;
    private Long idHeladera;
}
