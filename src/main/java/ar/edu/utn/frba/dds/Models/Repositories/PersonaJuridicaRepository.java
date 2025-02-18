package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Setter;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.List;

//@Repository
@Setter
public class PersonaJuridicaRepository implements IRepository, WithSimplePersistenceUnit {

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + PersonaJuridica.class.getName()).getResultList();
    }

    public Object buscarPorUsuario(long userId) {
        try {
            return entityManager().createQuery("from " + PersonaJuridica.class.getName() + " where usuario_id = :userId")
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Object buscar(Long id) {
        return find(PersonaJuridica.class, id);
    }

    @Override
    public void guardar(Object object) {
        if (object instanceof PersonaJuridica personaJuridica) {
            EntityTransaction tx = entityManager().getTransaction();
            try {
                tx.begin();

                // Guardar la entidad principal
                entityManager().persist(personaJuridica);
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        } else {
            throw new IllegalArgumentException("El objeto proporcionado no es una instancia de PersonaJuridica");
        }
    }

    @Override
    public void actualizar(Object object) {
        if (object instanceof PersonaJuridica personaJuridica) {
            personaJuridica.setFechaModificacion(LocalDateTime.now());
            withTransaction(() -> { entityManager().merge(personaJuridica);});
        } else {
            throw new IllegalArgumentException("El objeto proporcionado no es una instancia de PersonaJuridica");
        }
    }

    @Override
    public void eliminar(Long id) {
        PersonaJuridica personaJuridica = find(PersonaJuridica.class, id);
        personaJuridica.setActivo(false);
        withTransaction(() -> { entityManager().merge(personaJuridica);});
    }
}