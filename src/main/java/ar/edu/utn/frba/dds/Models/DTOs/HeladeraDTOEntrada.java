package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.Data;

@Data
public class HeladeraDTOEntrada {

    private DireccionDTOEntrada direccion;
    private String nombre;
    private Integer capacidad;
    private Long idModelo;
}
