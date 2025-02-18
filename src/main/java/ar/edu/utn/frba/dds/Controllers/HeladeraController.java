package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Models.DTOs.HeladeraDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.HeladeraDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.SessionDTO;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;
import ar.edu.utn.frba.dds.Services.*;
import io.javalin.http.Context;

import java.util.List;


public class HeladeraController{

    private HeladeraServiceImpl heladeraService;

    public HeladeraController(HeladeraServiceImpl heladeraService) {
        this.heladeraService = heladeraService;
    }

    public List<HeladeraDTOSalida> getAlertas(Long idPH, String tipoActor, Boolean solucionadas, SessionDTO sessionDTO) {
        return heladeraService.getAlertasHeladerasPersona(Long.valueOf(idPH), TipoActor.valueOf(tipoActor.toUpperCase()), solucionadas, sessionDTO);
    }

    public HeladeraDTOSalida getHeladera(Long id){
        return heladeraService.getHeladera(Long.valueOf(id));
    }

    public void reportarFalla(Context ctx, Long id, String username, Long idHeladera) {
    }

    public Object getAllMapa() {
        return heladeraService.getAllHeladerasMapa();
    }

    public Object getAll() {
            return heladeraService.getAllHeladeras();
    }
}
