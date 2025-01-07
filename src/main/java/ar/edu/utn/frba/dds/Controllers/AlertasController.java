package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Models.DTOs.IncidenteDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.SessionDTO;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;
import ar.edu.utn.frba.dds.Services.IncidenteServiceImpl;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.HashMap;

public class AlertasController {

    private HeladeraController heladeraController;

    private IncidenteServiceImpl incidenteService;

    private UsuarioController usuarioController;

    public AlertasController(HeladeraController heladeraController, IncidenteServiceImpl incidenteService, UsuarioController usuarioController) {
        this.heladeraController = heladeraController;
        this.incidenteService = incidenteService;
        this.usuarioController = usuarioController;
    }

    public void index(Context ctx, Long id, String username, SessionDTO sessionDTO) {

        HashMap<String, Object> model = new HashMap<>();
        model.put("alertas", heladeraController.getAlertas(id, ctx.sessionAttribute("tipoPersona").toString(), false, sessionDTO));
        model.put("vulnerable", ctx.sessionAttribute("tipoPersona"));
        model.put("username", username);
        String tipoPersona = ctx.sessionAttribute("tipoPersona");

        if (tipoPersona.equals(TipoActor.JURIDICA.toString().toLowerCase())) model.put("juridica", true);
        if (tipoPersona.equals(TipoActor.HUMANA.toString().toLowerCase())) model.put("humana", true);
        if (tipoPersona.equals(TipoActor.ADMINISTRADOR.toString().toLowerCase())) model.put("administrador", true);
        if (tipoPersona.equals(TipoActor.TECNICO.toString().toLowerCase())) model.put("tecnico", true);

        ctx.render("alertas/alertas.hbs", model);
    }

    public void reportarAlerta(Context ctx, Long id, String username) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("heladera", heladeraController.getHeladera(id));
        model.put("username", username);
        ctx.render("alertas/reportarFalla.hbs", model);
    }

    public void postAlerta(Context ctx) {
        if (ctx.sessionAttribute("tipoPersona") != null) {
            if (!ctx.sessionAttribute("tipoPersona").equals("humana") && !ctx.sessionAttribute("tipoPersona").equals("juridica") && !ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }
        HashMap<String, Object> model = new HashMap<>();

        IncidenteDTOEntrada incidenteDTOEntrada = new IncidenteDTOEntrada(ctx.formParam("mensaje"),
                ctx.formParam("tipoFalla"), LocalDateTime.now(), Long.valueOf(ctx.formParam("idHeladera")));

        incidenteService.reportarIncidente(incidenteDTOEntrada);

        String username = ctx.sessionAttribute("username");

        ctx.sessionAttribute("username", username);
        ctx.redirect("/");


//        model.put("username", username);
//        model.put("heladera", heladeraController.getHeladera(id));
        //ctx.render("alertas/falla.hbs", model);
    }

    public void getAlertasIncidente(Long idIncidente, Context ctx) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("incidente", incidenteService.getIncidente(idIncidente));
        ctx.sessionAttribute("incidente", incidenteService.getIncidente(idIncidente));
        SessionDTO sessionDTO = ctx.sessionAttribute("session");
        if (sessionDTO.getTipoPersona().equals("tecnico")) {
            model.put("tecnico", true);
        }
        ctx.render("alertas/alerta.hbs", model);
    }
}
