package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Resolucion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.NoResultException;


import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResolucionRepository implements IRepository, WithSimplePersistenceUnit {
    @Override
    public List buscarTodos() {
        return null;
    }

    @Override
    public Object buscar(Long id) {
        return find(Resolucion.class, id);
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
        if (object instanceof Heladera) {
            Resolucion resolucion = (Resolucion) object;
            resolucion.setFechaModificacion(LocalDateTime.now());
            withTransaction(() -> {
                entityManager().merge(resolucion);
            });
        } else {
            throw new IllegalArgumentException("El objeto proporcionado no es una instancia de" + Resolucion.class.getName());
        }
    }

    @Override
    public void eliminar(Long id) {
        Resolucion resolucion = find(Resolucion.class, id);
        resolucion.setActivo(false);
        resolucion.setFechaBaja(LocalDateTime.now());
        withTransaction(() -> {
            entityManager().merge(resolucion);
        });
    }


    public Resolucion buscarPorIncidente(Long id) {
        try {
            return (Resolucion) entityManager().createQuery("from " + Resolucion.class.getName() + " where incidente_id= :id and activo = true")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // or handle it in another appropriate way
        }
    }

    public List<Resolucion> getResolucionesIncidente(Long id) {
        try{
        return entityManager().createQuery("from " + Resolucion.class.getName() + " where incidente_id= :id and activo = true")
                .setParameter("id", id)
                .getResultList();}
        catch (NoResultException e) {
            return new ArrayList<>(); // or handle it in another appropriate way
        }
    }
}
