package ar.edu.utn.frba.dds.Controllers.Suscripciones;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.HeladeraDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.SessionDTO;
import ar.edu.utn.frba.dds.Models.DTOs.SuscripcionDTOEntrada;
import ar.edu.utn.frba.dds.Models.Repositories.SuscripcionRepository;
import ar.edu.utn.frba.dds.Services.*;
import ar.edu.utn.frba.dds.Utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuscripcionController implements ICrudViewsHandler {

    private SuscripcionRepository suscripcionRepository;

    private SuscripcionServiceImpl suscripcionService;

    private HeladeraServiceImpl heladeraService;

    public SuscripcionController(SuscripcionRepository suscripcionRepository, SuscripcionServiceImpl suscripcionService, HeladeraServiceImpl heladeraService) {
        this.suscripcionRepository = suscripcionRepository;
        this.suscripcionService = suscripcionService;
        this.heladeraService = heladeraService;
    }

    @Override
    public void index(Context ctx) {
        if (ctx.sessionAttribute("tipoPersona") != null) {
            if (!ctx.sessionAttribute("tipoPersona").equals("humana") && !ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }
        List<HeladeraDTOSalida> heladeras;
        if (ctx.sessionAttribute("tipoPersona").equals("juridica")) {
            heladeras = heladeraService.getHeladerasSuscPJ(ctx.sessionAttribute("id"));
        } else {
            heladeras = heladeraService.getHeladerasSuscPH(ctx.sessionAttribute("id"));
        }
        Map<String, Object> model = new HashMap<>();
        model.put("heladeras", heladeras);
        ctx.render("suscripciones/suscribirse.hbs", model);
    }

    public void suscribirse(Context ctx) {
        if (ctx.sessionAttribute("tipoPersona") != null) {
            if (!ctx.sessionAttribute("tipoPersona").equals("humana") && !ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }
        String tipoSuscripcion = ctx.formParam("tipo-suscripcion");
        String idHeladera = ctx.formParam("id-heladera");
        HashMap<String, Object> model = new HashMap<>();
        model.put(tipoSuscripcion, tipoSuscripcion);
        model.put("id-heladera", idHeladera);
        ctx.render("suscripciones/formSuscripcion.hbs", model);
    }

    public void guardarSuscripcion(Context ctx) {
        if (ctx.sessionAttribute("tipoPersona") != null) {
            if (!ctx.sessionAttribute("tipoPersona").equals("humana") && !ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }
        suscripcionService.save(setUpSuscripcionDTO(ctx));
//        switch (tipoSuscripcion) {
//            case "desperfecto":
//                ServiceLocator.instanceOf(SuscripcionDesperfectoController.class).save(ctx);
//                break;
//            case "reposicion":
//                ServiceLocator.instanceOf(SuscripcionReposicionController.class).save(ctx);
//                break;
//            case "distribucion":
//                ServiceLocator.instanceOf(SuscripcionDistribucionController.class).save(ctx);
//                break;
//        }
        ctx.redirect("/");
    }

    private SuscripcionDTOEntrada setUpSuscripcionDTO(Context ctx) {
        SessionDTO sessionDTO = ctx.sessionAttribute("session");
        return new SuscripcionDTOEntrada(
                Long.parseLong(ctx.formParam("id-heladera")),
                sessionDTO.getIdPersona(),
                ctx.formParam("tipo-suscripcion"),
                sessionDTO.getTipoPersona()
        );
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
