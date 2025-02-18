package ar.edu.utn.frba.dds.Models.Repositories;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityTransaction;

import ar.edu.utn.frba.dds.Models.Domain.Direccion.Direccion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class DireccionRepository implements IRepository, WithSimplePersistenceUnit {
    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Direccion.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(Direccion.class, id);
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
        if (object instanceof Direccion) {
            Direccion direccion = (Direccion) object;
            direccion.setFechaModificacion(LocalDateTime.now());
            withTransaction(() -> {
                entityManager().merge(direccion);
            });
        }
        else {
            throw new IllegalArgumentException("El objeto proporcionado no es una instancia de Direccion");
        }
    }

    @Override
    public void eliminar(Long id) {
        Direccion direccion = find(Direccion.class, id);
        direccion.setActivo(false);
        withTransaction(() -> { entityManager().merge(direccion); });
    }
}
