package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class HeladeraDTOSalida {
    private Long id;
    private String nombre;
    private Integer capacidad;
    private DireccionDTOSalida direccion;
    private ModeloDTOSalida modelo;
    private Boolean abierta;
    private EstadoHeladeraDTOSalida estado;
    private List<ViandaDTOSalida> viandas = new ArrayList<>();
    private List<IncidenteDTOSalida> incidentes1 = new ArrayList<>();

    public void addVianda(ViandaDTOSalida vianda){
        this.viandas.add(vianda);
    }

    public void addIncidente(IncidenteDTOSalida incidente){
        this.incidentes1.add(incidente);
    }
}
