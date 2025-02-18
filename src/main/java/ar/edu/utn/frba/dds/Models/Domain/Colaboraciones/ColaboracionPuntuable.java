package ar.edu.utn.frba.dds.Models.Domain.Colaboraciones;

import lombok.Data;

import java.util.List;

@Data
public abstract class ColaboracionPuntuable extends Colaboracion {
    public abstract Float calcularPuntaje();
}