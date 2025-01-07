package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TecnicoDTOSalida {
    private Long id;
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String numeroDocumento;
    private String cuil;
    private Long idUsuario;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private BigDecimal radio;
}
