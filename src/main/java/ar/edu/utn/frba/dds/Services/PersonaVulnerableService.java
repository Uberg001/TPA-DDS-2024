package ar.edu.utn.frba.dds.Services;


import ar.edu.utn.frba.dds.Models.DTOs.PersonaVulnerableDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaVulnerableDTOSalida;

import java.util.List;

public interface PersonaVulnerableService  {
    List<PersonaVulnerableDTOSalida> getAllUsers();

    PersonaVulnerableDTOSalida getUserById(long userId);

    PersonaVulnerableDTOSalida createUser(PersonaVulnerableDTOEntrada personaVulnerableDTOEntrada);

    Long updateUser(long userId, PersonaVulnerableDTOEntrada personaVulnerableDTOEntrada);

    void deleteUser(long userId);
}