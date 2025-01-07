package ar.edu.utn.frba.dds.Controllers.Suscripciones;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.HeladeraDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.SuscripcionDesperfecto;
import ar.edu.utn.frba.dds.Models.Repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.SuscripcionRepository;
import ar.edu.utn.frba.dds.Services.HeladeraServiceImpl;
import ar.edu.utn.frba.dds.Utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.util.List;

public class SuscripcionDesperfectoController implements ICrudViewsHandler {

    public SuscripcionRepository suscripcionRepository;

    public SuscripcionDesperfectoController(SuscripcionRepository suscripcionRepository) {
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
        String idHeladera = context.formParam("id-heladera");
        Long idPersona = context.sessionAttribute("id");
        Heladera heladera = (Heladera) ServiceLocator.instanceOf(HeladeraRepository.class).buscar(Long.valueOf(idHeladera));
        PersonaHumana persona = (PersonaHumana) ServiceLocator.instanceOf(PersonaHumanaRepository.class).buscarPorUsuarioId(idPersona);
        SuscripcionDesperfecto suscripcionDesperfecto = (SuscripcionDesperfecto) this.suscripcionRepository.buscarPorTipo("desperfecto");
        if (suscripcionDesperfecto == null) {
            suscripcionDesperfecto = new SuscripcionDesperfecto();
        }
        if (!suscripcionDesperfecto.getHeladeras().contains(heladera)) {
            suscripcionDesperfecto.addHeladera(heladera);
            heladera.agregarSuscripcion(suscripcionDesperfecto);
        }
        if (!suscripcionDesperfecto.getPersonas().contains(persona)) {
            suscripcionDesperfecto.addPersona(persona);
            persona.suscribirse(suscripcionDesperfecto);
        }
        this.suscripcionRepository.guardar(suscripcionDesperfecto);
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
