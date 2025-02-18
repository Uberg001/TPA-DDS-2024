package ar.edu.utn.frba.dds.Models.Repositories;

import javax.persistence.EntityTransaction;

import java.util.List;
import java.time.LocalDateTime;

import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.RegistrarPersonasVulnerables;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RegistrarPersonaVulnerableRepository implements IRepository, WithSimplePersistenceUnit {
    @Override
    public List<RegistrarPersonasVulnerables> buscarTodos() {
        return entityManager().createQuery("from " + RegistrarPersonasVulnerables.class.getName(), RegistrarPersonasVulnerables.class).getResultList();
    }

    @Override
    public RegistrarPersonasVulnerables buscar(Long id) {
        return find(RegistrarPersonasVulnerables.class, id);
    }

    @Override
    public void guardar(Object obj) {
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(obj);
        tx.commit();
    }

    @Override
    public void actualizar(Object obj){
        
    }

    @Override
    public void eliminar(Long id) {
        RegistrarPersonasVulnerables registrarPersonaVulnerable = find(RegistrarPersonasVulnerables.class, id);
        registrarPersonaVulnerable.setActivo(false);
        withTransaction(() -> entityManager().merge(registrarPersonaVulnerable));
    }
}
