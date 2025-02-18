package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Utils.Canje;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import java.util.List;

public class CanjeRepository implements IRepository, WithSimplePersistenceUnit {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Canje.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(Canje.class, id);
    }

    @Override
    public void guardar(Object object) {
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(object);
        tx.commit();
    }

    @Override
    public void actualizar(Object object) {
        withTransaction(() -> { entityManager.merge(object); });
    }

    @Override
    public void eliminar(Long id) {
        Canje canje = find(Canje.class, id);
        canje.setActivo(false);
        withTransaction(() -> { entityManager.merge(canje); });
    }
}