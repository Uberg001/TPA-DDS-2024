package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.ResolucionDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.ResolucionDTOSalida;

import java.util.List;

public interface ResolucionService {

    void createResolucion(ResolucionDTOEntrada resolucionDTOEntrada);

    List<ResolucionDTOSalida> getResolucionesIncidente(Long id);
}
