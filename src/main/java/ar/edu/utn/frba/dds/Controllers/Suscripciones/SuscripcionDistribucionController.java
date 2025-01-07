package ar.edu.utn.frba.dds.Controllers.Suscripciones;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.SuscripcionDistribucion;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.SuscripcionReposicion;
import ar.edu.utn.frba.dds.Models.Repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.SuscripcionRepository;
import ar.edu.utn.frba.dds.Utils.ICrudViewsHandler;
import io.javalin.http.Context;

public class SuscripcionDistribucionController implements ICrudViewsHandler {

    private SuscripcionRepository suscripcionRepository;

    public SuscripcionDistribucionController(SuscripcionRepository suscripcionRepository) {
        this.suscripcionRepository = suscripcionRepository;
    }

    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) {
        String idHeladera = context.formParam("id-heladera");
        Long idPersona = context.sessionAttribute("id");
        Heladera heladera = (Heladera) ServiceLocator.instanceOf(HeladeraRepository.class).buscar(Long.valueOf(idHeladera));
        PersonaHumana persona = (PersonaHumana) ServiceLocator.instanceOf(PersonaHumanaRepository.class).buscarPorUsuarioId(idPersona);
        int cantidadMaximaViandas = Integer.valueOf(context.formParam("viandas-maximas"));
        SuscripcionDistribucion suscripcionDistribucion = (SuscripcionDistribucion) this.suscripcionRepository.buscarPorTipoYCantidadMaximaViandas("distribucion", cantidadMaximaViandas);

        if (suscripcionDistribucion == null) {
            suscripcionDistribucion = new SuscripcionDistribucion();
            suscripcionDistribucion.setCantidadDeViandasMaximas(cantidadMaximaViandas);
        }

        if (!suscripcionDistribucion.getHeladeras().contains(heladera)) {
            suscripcionDistribucion.addHeladera(heladera);
            heladera.agregarSuscripcion(suscripcionDistribucion);
        }

        if (!suscripcionDistribucion.getPersonas().contains(persona)) {
            suscripcionDistribucion.addPersona(persona);
            persona.suscribirse(suscripcionDistribucion);
        }

        // Ensure the same instance of persona is used throughout the session
        persona = (PersonaHumana) this.suscripcionRepository.merge(persona);
        this.suscripcionRepository.guardar(suscripcionDistribucion);
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
