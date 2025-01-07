package ar.edu.utn.frba.dds.Models.Repositories;

import java.util.List;


import javax.persistence.EntityTransaction;

import ar.edu.utn.frba.dds.Models.Domain.Utils.Oferta;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class OfertaRepository implements IRepository, WithSimplePersistenceUnit{
    @Override
    public List buscarTodos() {
         return entityManager().createQuery("from " + Oferta.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return entityManager().find(Oferta.class, id);
    }

    @Override
    public void guardar(Object object) {
        EntityTransaction tx = entityManager().getTransaction();
        try {
            tx.begin();
            entityManager().merge(object);
            tx.commit();
            entityManager().detach(object);  // Detach to prevent multiple instances in future merges
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

    @Override
    public void actualizar(Object object) {

    }

    @Override
    public void eliminar(Long id) {

    }
}
