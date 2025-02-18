package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Controllers.Colaboraciones.*;
import ar.edu.utn.frba.dds.Models.DTOs.HeladeraDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.OfrecerProductos;
import ar.edu.utn.frba.dds.Models.Repositories.ColaboracionRepository;
import ar.edu.utn.frba.dds.Services.*;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.Config.ServiceLocator;
import java.util.HashMap;
import java.util.List;

public class ColaboracionController{

    private ColaboracionRepository colaboracionRepository;
    private ModeloServiceImpl modeloService;

    public ColaboracionController(ColaboracionRepository colaboracionRepository, ModeloServiceImpl modeloService) {
        this.colaboracionRepository = colaboracionRepository;
        this.modeloService = modeloService;
    }

    public void index(Context ctx, String tipoUsuario){
        if(ctx.sessionAttribute("tipoPersona") != null) {
            if(!ctx.sessionAttribute("tipoPersona").equals("humana") && !ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }
        HashMap<String, Object> model = new HashMap<>();
        model.put(tipoUsuario,tipoUsuario);
        ctx.render("cargarColaboracion/colaboracion.hbs",model);
    }

    public void formColaboracion(Context ctx){
        if(ctx.sessionAttribute("tipoPersona") != null) {
            if(!ctx.sessionAttribute("tipoPersona").equals("humana") && !ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }
        String accion = ctx.queryParam("accion");
        HashMap<String, Object> model = new HashMap<>();
        model.put(accion,accion);
        if(accion.equals("donar-vianda") || accion.equals("distribuir-vianda")){
            List<HeladeraDTOSalida> helas = ServiceLocator.instanceOf(HeladeraServiceImpl.class).getAllHeladeras();
            model.put("heladeras",helas);
        }

        if (accion.equals("cargo-heladera")){
            model.put("modelos",modeloService.getAllModelos());
        }
        ctx.render("formCargarColaboracion/formCargarColaboracion.hbs",model);
    }

    public void cargarColaboracion(Context ctx){
        if(ctx.sessionAttribute("tipoPersona") != null) {
            if(!ctx.sessionAttribute("tipoPersona").equals("humana") && !ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }
        String accion = ctx.formParam("accion");
        if(accion == null || accion.isEmpty()) {
            ctx.redirect("/");
            return;
        }
        try{
            switch (accion){
                case "donar-dinero":
                    ServiceLocator.instanceOf(DonarDineroController.class).cargarColaboracion(ctx);
                    break;
                case "donar-vianda":
                    ServiceLocator.instanceOf(DonarViandaController.class).cargarColaboracion(ctx);
                    break;
                case "distribuir-vianda":
                    ServiceLocator.instanceOf(DistribuirViandasController.class).cargarColaboracion(ctx);
                    break;
                case "cargo-heladera":
                    ServiceLocator.instanceOf(HacerseCargoDeHeladerasController.class).cargarColaboracion(ctx);
                    break;
                case "registrar-pv":
                    ServiceLocator.instanceOf(RegistrarPersonasVulnerablesController.class).cargarPersonaVulnerable(ctx);;
                    break;
                case "ofrecer-productos":
                    ServiceLocator.instanceOf(OfertaController.class).cargarOferta(ctx);;
                    break;
                default:
                    ctx.redirect("/");
                    break;
            }
        } catch (Exception e){
            ctx.status(500).render("errors/500.hbs");
        }
    }

}
