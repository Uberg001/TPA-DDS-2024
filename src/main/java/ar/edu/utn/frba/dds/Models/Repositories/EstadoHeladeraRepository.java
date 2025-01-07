package ar.edu.utn.frba.dds.Models.Repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.EstadoHeladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class EstadoHeladeraRepository implements IRepository, WithSimplePersistenceUnit {

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + EstadoHeladera.class.getName()).getResultList();
    }

    @Override
    public void actualizar(Object entity) {
        // Implementation here
    }

    @Override
    public Object buscar(Long id) {
        // Implementation here
        return null;
    }

    @Override
    public void eliminar(Long id) {
        // Implementation here
    }

    @Override
    public void guardar(Object entity) {
        // Implementation here
    }
    @PersistenceContext
    private EntityManager entityManager;

}
