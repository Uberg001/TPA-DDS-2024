package ar.edu.utn.frba.dds.Controllers;

import io.javalin.http.Context;

public class DashboardController {
    
    public void index(Context ctx) {
        if(ctx.sessionAttribute("tipoPersona") != null) {
            if(!ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            } else ctx.render("dashboard/dashboard.hbs");
        } else {
            ctx.redirect("/login");
            return;
        }
    }

    public void mostrarDB(Context ctx) {
        if(ctx.sessionAttribute("tipoPersona") != null) {
            if(!ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            } else ctx.render("dashboard/DB_View/baseDeDatos.hbs");
        } else {
            ctx.redirect("/login");
            return;
        }
    }
}
