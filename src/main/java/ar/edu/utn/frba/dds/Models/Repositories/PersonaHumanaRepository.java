package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Utils.EntityManagerProvider;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import java.time.LocalDateTime;
import java.util.List;

public class PersonaHumanaRepository implements IRepository, WithSimplePersistenceUnit {

    //private EntityManager entityManager = EntityManagerProvider.getEntityManager();

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + PersonaHumana.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(PersonaHumana.class, id);
    }

    public Object buscarPorDocumento(Long documento) {
        return find(PersonaHumana.class, documento);
    }

    public Object buscarPorUsuarioId(Long id) {
        return entityManager().createQuery("from " + PersonaHumana.class.getName() + " where usuario_id = :id")
                .setParameter("id", id)
                .getSingleResult();
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
        if (object instanceof PersonaHumana) {
            PersonaHumana personaHumana = (PersonaHumana) object;
            personaHumana.setFechaModificacion(LocalDateTime.now());

            withTransaction(() -> {
                    entityManager().merge(object);
                }
            );
        } else {
            throw new IllegalArgumentException("El objeto proporcionado no es una instancia de PersonaHumana");
        }
    }

    @Override
    public void eliminar(Long id) {
        PersonaHumana personaHumana = find(PersonaHumana.class, id);
        personaHumana.setActivo(false);
        withTransaction(() -> {
            entityManager().merge(personaHumana);
        });
    }


    public Object buscarPorUsuario(long userId) {
        try {
            return entityManager().createQuery("from " + PersonaHumana.class.getName() + " where usuario_id = :userId")
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}


