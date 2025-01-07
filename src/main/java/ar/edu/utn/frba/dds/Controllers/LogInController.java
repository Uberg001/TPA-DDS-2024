package ar.edu.utn.frba.dds.Controllers;

import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class LogInController {
    public void index(Context ctx){
        String persona = ctx.queryParam("persona");
        Map<String, Object> model = new HashMap<>();
        if(persona != null){
            if (persona.equals("humana")) {
                model.put("humana", "humana");
            } else if (persona.equals("juridica")){
                model.put("juridica", "juridica");
            } else if (persona.equals("administrador")){
                model.put("administrador", "administrador");
            } else if(persona.equals("tecnico")){
                model.put("tecnico", "tecnico");
            }
        }
        ctx.render("logIn/signIn.hbs",model);
    }
}
