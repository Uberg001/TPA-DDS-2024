package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.Suscripcion;
import ar.edu.utn.frba.dds.Utils.EntityManagerProvider;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import java.util.List;

public class SuscripcionRepository implements IRepository, WithSimplePersistenceUnit {

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Suscripcion.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(Suscripcion.class, id);
    }

    @Override
    public void guardar(Object object) {
        if (object instanceof Suscripcion suscripcion) {
            EntityTransaction tx = entityManager().getTransaction();
            try {
                tx.begin();
                entityManager().persist(suscripcion);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        } else {
            throw new IllegalArgumentException("El objeto proporcionado no es una instancia de Suscripcion");
        }
    }

    public Object buscarPorTipo(String tipo) {
        try {
            return entityManager().createQuery("from Suscripcion where tipo = :tipo")
                    .setParameter("tipo", tipo)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Object buscarPorTipoYCantidadMaximaViandas(String tipo, int cantidadDeViandasMaximas) {
        try {
            List resultList = entityManager().createQuery("from Suscripcion where tipo = :tipo and cantidadDeViandasMaximas = :cantidadDeViandasMaximas")
                    .setParameter("tipo", tipo)
                    .setParameter("cantidadDeViandasMaximas", cantidadDeViandasMaximas)
                    .getResultList();
            return resultList.isEmpty() ? null : resultList.get(0);
        } catch (NoResultException e) {
            return null;
        }
    }

    public Object buscarPorTipoYCantidadMinimaViandas(String tipo, int cantidadDeViandasMinimas) {
        try {
            return entityManager().createQuery("from Suscripcion where tipo = :tipo and cantidadDeViandasMinimas = :cantidadDeViandasMinimas")
                    .setParameter("tipo", tipo)
                    .setParameter("cantidadDeViandasMinimas",cantidadDeViandasMinimas)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void actualizar(Object object) {
        withTransaction(() -> { entityManager().merge(object);});
    }

    @Override
    public void eliminar(Long id) {
        Suscripcion suscripcion = find(Suscripcion.class, id);
        suscripcion.setActivo(false);
        withTransaction(() -> { entityManager().merge(suscripcion);});
    }
}