package ar.edu.utn.frba.dds.Models.Domain.Actores;

import ar.edu.utn.frba.dds.Models.Domain.Contacto.Contacto;

import java.util.ArrayList;

public interface PersonaColaboradora extends Persona {
    void calcularPuntaje();
    ArrayList<Contacto> obtenerContactos();
}
