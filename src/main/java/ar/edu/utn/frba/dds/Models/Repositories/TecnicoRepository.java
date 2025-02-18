package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Tecnico;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;

public class TecnicoRepository implements IRepository, WithSimplePersistenceUnit {

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Tecnico.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(Tecnico.class, id);
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

    public Object buscarPorUsuario(long userId) {
        try {
            return entityManager().createQuery("from " + Tecnico.class.getName() + " where usuario_id = :userId")
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
