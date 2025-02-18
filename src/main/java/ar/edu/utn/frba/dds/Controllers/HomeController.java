package ar.edu.utn.frba.dds.Controllers;
import ar.edu.utn.frba.dds.Utils.Entities.TipoUsuario;
import io.javalin.http.Context;

import java.util.HashMap;


public class HomeController {
    public void index(Context ctx) {
        HashMap<String, Object> model = new HashMap<>();
        if (ctx.sessionAttribute("tipoPersona") != null) {
            if (ctx.sessionAttribute("tipoPersona").equals("humana") || ctx.sessionAttribute("tipoPersona").equals("juridica")) {
                model.put("colaboradora", ctx.sessionAttribute("tipoPersona"));
                if (ctx.sessionAttribute("username") != null) {
                    model.put("username", ctx.sessionAttribute("username"));
                }
            } else if (ctx.sessionAttribute("tipoPersona").equals("vulnerable")) {
                model.put("vulnerable", ctx.sessionAttribute("tipoPersona"));
            }
            model.put(ctx.sessionAttribute("tipoPersona"), ctx.sessionAttribute("tipoPersona"));
        }
        if (ctx.sessionAttribute("username") != null) {
            model.put("username", ctx.sessionAttribute("username"));
        }
        ctx.render("home/home.hbs", model);
    }
}
