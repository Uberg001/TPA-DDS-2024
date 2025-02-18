package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.DistribuirViandas;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class DistribuirViandasRepository implements IRepository, WithSimplePersistenceUnit {
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
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(object);
        tx.commit();
    }

    @Override
    public void actualizar(Object object) {

    }

    @Override
    public void eliminar(Long id) {

    }
}
