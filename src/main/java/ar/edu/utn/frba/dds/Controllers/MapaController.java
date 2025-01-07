package ar.edu.utn.frba.dds.Controllers;

import io.javalin.http.Context;

import java.util.HashMap;

public class MapaController {
    private HeladeraController heladeraController;
    private TecnicoController tecnicoController;

    public MapaController(HeladeraController heladeraController, TecnicoController tecnicoController) {
        this.heladeraController = heladeraController;
        this.tecnicoController = tecnicoController;
    }

    public void index(Context ctx) {
        HashMap<String, Object> model = new HashMap<>();
        model.put("heladeras", heladeraController.getAllMapa());
        model.put("tecnicos", tecnicoController.getAllTecnicos());
        ctx.render("mapa/mapa.hbs", model);
    }
}
