package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class PuntajeRepository implements WithSimplePersistenceUnit {

    @PersistenceContext
    private EntityManager entityManager;





}
