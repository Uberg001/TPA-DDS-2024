package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.Acceso;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

public class AccesoRepository implements IRepository, WithSimplePersistenceUnit {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Acceso.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(Acceso.class, id);
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
        if (object instanceof Acceso) {
            Acceso acceso = (Acceso) object;
            acceso.setFechaModificacion(LocalDateTime.now());
            withTransaction(() -> {
                entityManager().merge(acceso);
            });
        }
        else {
            throw new IllegalArgumentException("El objeto proporcionado no es una instancia de Acceso");
        }
    }

    @Override
    public void eliminar(Long id) {
        Acceso acceso = find(Acceso.class, id);
        acceso.setActivo(false);
        withTransaction(() -> { entityManager().merge(acceso); });
    }
}