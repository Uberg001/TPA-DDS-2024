package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.PersonaJuridicaDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaJuridicaDTOSalida;

import java.util.List;

public interface PersonaJuridicaService {

    List<PersonaJuridicaDTOSalida> getAllUsers();

    PersonaJuridicaDTOSalida getUserById(long userId);

    PersonaJuridicaDTOSalida createUser(PersonaJuridicaDTOEntrada personaJuridicaDTOEntrada);

    PersonaJuridicaDTOSalida updateUser(long userId, PersonaJuridicaDTOEntrada personaJuridicaDTOEntrada);

    Float getPoints(long userId);

    void deleteUser(long userId);

    PersonaJuridicaDTOSalida getByUserId(long userId);
}