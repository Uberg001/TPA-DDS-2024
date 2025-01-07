package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.HeladeraDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.HeladeraDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.Response;
import ar.edu.utn.frba.dds.Models.DTOs.SessionDTO;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;

import java.util.List;

public interface HeladeraService {

    Long createHeladera(HeladeraDTOEntrada heladeraDTOEntrada);

    List<HeladeraDTOSalida> getAllHeladeras();

    List<HeladeraDTOSalida> getHeladerasByLocalidad(String localidad);

    Response agregarVianda(Long idHeladera, Long idVianda);

    HeladeraDTOSalida getHeladera(Long id);

    List<HeladeraDTOSalida> getAlertasHeladerasPersona(Long id, TipoActor tipoActor, Boolean solucionadas, SessionDTO sessionDTO);

    List<HeladeraDTOSalida> getHeladerasSuscPJ(Long id);

    Object getAllHeladerasMapa();
}
