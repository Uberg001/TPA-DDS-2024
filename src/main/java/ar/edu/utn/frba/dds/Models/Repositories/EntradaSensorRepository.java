package ar.edu.utn.frba.dds.Models.Repositories;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Entrada.EntradaSensor;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class EntradaSensorRepository implements IRepository, WithSimplePersistenceUnit {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + EntradaSensor.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(EntradaSensor.class, id);
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
        if (object instanceof EntradaSensor) {
            EntradaSensor entradaSensor = (EntradaSensor) object;
            entradaSensor.setFechaModificacion(LocalDateTime.now());
            withTransaction(() -> {
                entityManager().merge(entradaSensor);
            });
        }
        else {
            throw new IllegalArgumentException("El objeto proporcionado no es una instancia de EntradaSensor");
        }
    }

    @Override
    public void eliminar(Long id) {
        EntradaSensor entradaSensor = find(EntradaSensor.class, id);
        entradaSensor.setActivo(false);
        withTransaction(() -> { entityManager().merge(entradaSensor); });
    }
}
