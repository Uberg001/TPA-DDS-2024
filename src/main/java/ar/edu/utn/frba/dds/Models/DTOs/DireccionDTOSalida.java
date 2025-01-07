package ar.edu.utn.frba.dds.Models.DTOs;

import ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas.Coordenada;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DireccionDTOSalida {
    private String numero;
    private String calle;
    private int altura;
    private String localidad;
    private BigDecimal latitud;
    private BigDecimal longitud;

    public Coordenada getCoordenada() {
        return new Coordenada(latitud, longitud);
    }
}
