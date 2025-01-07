package ar.edu.utn.frba.dds.Models.Domain.Enviadores;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaColaboradora;

public interface Enviador {
    void enviar(Mensaje mensaje);
    String obtenerContacto(PersonaColaboradora persona);
}
