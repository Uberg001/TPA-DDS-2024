package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.Modelo;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.Suscripcion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

public class ModeloRepository implements IRepository, WithSimplePersistenceUnit {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Modelo.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(Modelo.class, id);
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
        Modelo modelo = find(Modelo.class, id);
        modelo.setActivo(false);
        withTransaction(() -> { entityManager.merge(modelo);});
    }
}
