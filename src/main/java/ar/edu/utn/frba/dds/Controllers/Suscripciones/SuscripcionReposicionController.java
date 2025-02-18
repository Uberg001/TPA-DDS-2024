package ar.edu.utn.frba.dds.Controllers.Suscripciones;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.SuscripcionDesperfecto;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.SuscripcionReposicion;
import ar.edu.utn.frba.dds.Models.Repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.SuscripcionRepository;
import ar.edu.utn.frba.dds.Utils.ICrudViewsHandler;
import io.javalin.http.Context;

public class SuscripcionReposicionController implements ICrudViewsHandler {

    private SuscripcionRepository suscripcionRepository;

    public SuscripcionReposicionController(SuscripcionRepository suscripcionRepository) {
        this.suscripcionRepository = suscripcionRepository;
    }

    public void index(Context context) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {
        String idHeladera                               = context.formParam("id-heladera");
        Long idPersona                                  = context.sessionAttribute("id");
        Heladera heladera                               = (Heladera) ServiceLocator.instanceOf(HeladeraRepository.class).buscar(Long.valueOf(idHeladera));
        PersonaHumana persona                           = (PersonaHumana) ServiceLocator.instanceOf(PersonaHumanaRepository.class).buscarPorUsuarioId(idPersona);
        int cantidadMinimasViandas                      = Integer.valueOf(context.formParam("viandas-minimas"));
        SuscripcionReposicion suscripcionReposicion     = (SuscripcionReposicion) this.suscripcionRepository.buscarPorTipoYCantidadMinimaViandas("reposicion",cantidadMinimasViandas);
        if(suscripcionReposicion == null){
            suscripcionReposicion = new SuscripcionReposicion();
            suscripcionReposicion.setCantidadDeViandasMinimas(cantidadMinimasViandas);
        }
        if(!suscripcionReposicion.getHeladeras().contains(heladera)){
            suscripcionReposicion.addHeladera(heladera);
            heladera.agregarSuscripcion(suscripcionReposicion);
        }
        if(!suscripcionReposicion.getPersonas().contains(persona)){
            suscripcionReposicion.addPersona(persona);
            persona.suscribirse(suscripcionReposicion);
        }
        this.suscripcionRepository.guardar(suscripcionReposicion);
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
