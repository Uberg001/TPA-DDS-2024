package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.Data;

@Data
public class OfertaDTOEntrada {
    private String nombre;
    private Float puntosNecesarios;
    private String rubro;
    private String imagen;
}
