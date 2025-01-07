package ar.edu.utn.frba.dds.Models.Domain.Utils;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;

public class CalculadorDeHeladerasActivas {
    public int obtenerHeladerasActivas(PersonaJuridica personaJuridica){
        return personaJuridica.calcularHeladerasActivas();
    }
}
