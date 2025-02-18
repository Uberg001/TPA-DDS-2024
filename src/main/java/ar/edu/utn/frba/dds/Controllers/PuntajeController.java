package ar.edu.utn.frba.dds.Controllers;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.Suscripcion;
import ar.edu.utn.frba.dds.Models.Repositories.PuntajeRepository;
import ar.edu.utn.frba.dds.Models.Repositories.SuscripcionRepository;
import ar.edu.utn.frba.dds.Services.*;
import ar.edu.utn.frba.dds.Utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuntajeController implements ICrudViewsHandler {

    private PuntajeRepository suscripcionRepository;

    @Override
    public void index(Context context) {
       // List<PersonaJuridica> pj = this.


//        List<Suscripcion> suscripciones = this.suscripcionRepository.buscarTodos();
//        Map<String, Object> model = new HashMap<>();
//        model.put("suscripciones", suscripciones);
//        model.put("titulo", "Listado de suscripciones");
//        context.render("suscripciones/suscripciones.hbs",model);
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
