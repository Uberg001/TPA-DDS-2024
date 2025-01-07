package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.HeladeraDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.ModeloDTOSalida;

import java.util.List;

public interface ModeloService {

    List<ModeloDTOSalida> getAllModelos();
}
