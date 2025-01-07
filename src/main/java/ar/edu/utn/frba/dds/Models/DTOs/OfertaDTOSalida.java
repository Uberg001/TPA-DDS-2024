package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.Data;

@Data
public class OfertaDTOSalida {
    private String nombre;
    private Float puntosNecesarios;
    private String imagen;
    private String rubro;
    private String id;
}
