package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.*;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;

import java.util.List;

public interface PersonaHumanaService {

    List<PersonaHumanaDTOSalida> obtenerTodos();

    PersonaHumanaDTOSalida getById(long userId);

    PersonaHumanaDTOSalida createPH(PersonaHumanaDTOEntrada personaHumanaDTOEntrada);

    Long updatePH(long userId, PersonaHumanaDTOEntrada personaHumanaDTOEntrada);

    void deletePH(long userId);
    Float getPoints(long userId);

}
