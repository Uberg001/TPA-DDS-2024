package ar.edu.utn.frba.dds.Models.Repositories;

import java.util.List;

import ar.edu.utn.frba.dds.Models.Domain.Utils.EstadoVisita;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class EstadoVisitaRepository implements IRepository, WithSimplePersistenceUnit {
    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + EstadoVisita.class.getName()).getResultList();
    }

    @Override
    public void actualizar(Object entity) {
        entityManager().merge(entity);
    }

    @Override
    public Object buscar(Long id) {
        return entityManager().find(EstadoVisita.class, id);
    }

    @Override
    public void eliminar(Long id) {
        EstadoVisita entity = entityManager().find(EstadoVisita.class, id);
        if (entity != null) {
            entityManager().remove(entity);
        }
    }

    @Override
    public void guardar(Object entity) {
        entityManager().persist(entity);
    }
}
