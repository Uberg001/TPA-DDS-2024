package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.Colaboracion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class DonarDineroRepository implements IRepository, WithSimplePersistenceUnit {
    @Override
    public List buscarTodos() {
        return List.of();
    }

    @Override
    public Object buscar(Long id) {
        return null;
    }

    @Override
    public void guardar(Object object) {
        if (object instanceof Colaboracion colaboracion) {
            EntityTransaction tx = entityManager().getTransaction();
            try {
                tx.begin();
                entityManager().persist(colaboracion);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        } else {
            throw new IllegalArgumentException("El objeto proporcionado no es una instancia de DonarDineroRepository");
        }

    }

    @Override
    public void actualizar(Object object) {

    }

    @Override
    public void eliminar(Long id) {

    }
}
