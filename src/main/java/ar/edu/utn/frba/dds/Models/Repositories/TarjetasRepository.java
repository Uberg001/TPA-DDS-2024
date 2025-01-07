package ar.edu.utn.frba.dds.Models.Repositories;


import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.Tarjeta;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import java.util.List;


public class TarjetasRepository implements IRepository, WithSimplePersistenceUnit {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Tarjeta.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(Tarjeta.class, id);
    }

    @Override
    public void guardar(Object object) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(object);
        tx.commit();
    }


    @Override
    public void actualizar(Object object) {
        withTransaction(() -> { entityManager.merge(object);});
    }

    @Override
    public void eliminar(Long id) {
        Tarjeta tarjeta = find(Tarjeta.class, id);
        tarjeta.setActivo(false);
        withTransaction(() -> { entityManager.merge(tarjeta);});
    }
}


