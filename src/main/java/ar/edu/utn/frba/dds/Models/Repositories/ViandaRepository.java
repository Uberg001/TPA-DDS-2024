package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;
import ar.edu.utn.frba.dds.Utils.EntityManagerProvider;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class ViandaRepository implements IRepository, WithSimplePersistenceUnit {

    @PersistenceContext
    private EntityManager entityManager= EntityManagerProvider.getEntityManager();;

    @Override
    public List buscarTodos() {
        return entityManager.createQuery("from " + Vianda.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(Vianda.class, id);
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
        Vianda vianda = find(Vianda.class, id);
        vianda.setActivo(false);
        withTransaction(() -> { entityManager.merge(vianda);});
    }
}
