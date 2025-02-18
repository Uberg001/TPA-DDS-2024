package ar.edu.utn.frba.dds.Models.Repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.AperturaHeladera;
import ar.edu.utn.frba.dds.Utils.EntityManagerProvider;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class AperturaHeladeraRepository implements IRepository, WithSimplePersistenceUnit {
    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + AperturaHeladera.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return null;
    }

    @Override
    public void guardar(Object object) {

    }

    @Override
    public void actualizar(Object object) {

    }

    @Override
    public void eliminar(Long id) {

    }
}
