package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.SuscripcionDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.SuscripcionDTOSalida;

import java.util.List;

public interface SuscripcionService {

    List<SuscripcionDTOSalida> getAllSuscripciones();

    List<SuscripcionDTOSalida> getSuscripcionByPersona(Long idPersona);

    void save(SuscripcionDTOEntrada suscripcionDTOEntrada);
}
