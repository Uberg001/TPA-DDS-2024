package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DireccionDTOEntrada {

    private DireccionDTOEntrada direccion;
    private String nombre;
    private Integer capacidad;
    private LocalDate fechaInstalacion;
    private Long idModelo;
    private String calle;
    private Integer altura;
    private String localidad;
    private String provincia;
}
