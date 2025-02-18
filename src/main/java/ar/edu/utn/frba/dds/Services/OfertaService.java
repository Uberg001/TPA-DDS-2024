package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.OfertaDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.OfertaDTOSalida;

import java.util.List;

public interface OfertaService {

    public void cargarOferta(OfertaDTOEntrada ofertaDTO);
    public void canjearPuntos(String tipoUsuario);
    public List<OfertaDTOSalida> obtenerOfertas();
}
