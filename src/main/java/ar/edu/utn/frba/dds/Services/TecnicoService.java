package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.TecnicoDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.TecnicoDTOSalida;

import java.util.List;

public interface TecnicoService {

    TecnicoDTOSalida createTecnico(TecnicoDTOEntrada tecnicoDTOEntrada);

    TecnicoDTOSalida getByUserId(Long id);

    List<TecnicoDTOSalida> getAllTecnicos();
}
