package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Models.DTOs.ResolucionDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.SessionDTO;

import ar.edu.utn.frba.dds.Services.ResolucionServiceImpl;
import io.javalin.http.Context;
import java.util.HashMap;

public class ResolucionController {

    private ResolucionServiceImpl resolucionService;

    public ResolucionController(ResolucionServiceImpl resolucionService) {
        this.resolucionService = resolucionService;
    }

    public void index(Context ctx, SessionDTO sessionDTO, Long idIncidente) {
        ctx.sessionAttribute("idIncidente", idIncidente);
        ctx.sessionAttribute("session", sessionDTO);
        HashMap<String, Object> model = new HashMap<>();
//        model.put("heladera", heladeraController.getHeladera(id));
//        model.put("username", username);
        model.put("idIncidente", idIncidente);
        model.put("session", sessionDTO);
        ctx.render("resolucion/resolucion.hbs", model);
    }

    public void resolver(Context ctx, SessionDTO sessionDTO, Long idIncidente) {
        ResolucionDTOEntrada resolucionDTOEntrada = setResolucionDTOEntrada(ctx, sessionDTO, idIncidente);
        resolucionService.createResolucion(resolucionDTOEntrada);
        ctx.redirect("/");
    }

    private ResolucionDTOEntrada setResolucionDTOEntrada(Context ctx,SessionDTO sessionDTO, Long idIncidente) {
        ResolucionDTOEntrada resolucionDTOEntrada = new ResolucionDTOEntrada();
        resolucionDTOEntrada.setDescripcion(ctx.formParam("descripcion"));
        resolucionDTOEntrada.setFoto_path(ctx.formParam("imagen"));
        resolucionDTOEntrada.setIdIncidente(ctx.sessionAttribute("idIncidente"));
        resolucionDTOEntrada.setIdTecnico((sessionDTO.getIdPersona()));
        resolucionDTOEntrada.setEstadoHeladera(ctx.formParam("estado"));
        return resolucionDTOEntrada;
    }
}
