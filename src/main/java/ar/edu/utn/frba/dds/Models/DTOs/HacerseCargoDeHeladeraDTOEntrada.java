package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor @Data
public class HacerseCargoDeHeladeraDTOEntrada {

    private String calle;
    private Integer altura;
    private String localidad;
    private String provincia;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String nombre;
    private LocalDate fechaInstalacion;
    private Long modelo;
    private Long idUsuario;

}
