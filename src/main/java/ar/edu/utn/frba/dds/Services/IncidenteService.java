package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.IncidenteDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.IncidenteDTOSalida;

public interface IncidenteService {
    void reportarIncidente(IncidenteDTOEntrada incidenteDTOEntrada);
    IncidenteDTOSalida getIncidente(Long id);
}
