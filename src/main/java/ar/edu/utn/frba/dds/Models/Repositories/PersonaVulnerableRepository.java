package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;


//@Repository
public class PersonaVulnerableRepository implements IRepository, WithSimplePersistenceUnit /*extends JpaRepository<PersonaVulnerable, Long>*/
{

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + PersonaVulnerable.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(PersonaVulnerable.class, id);
    }

    @Override
    public void guardar(Object object) {
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(object);
        tx.commit();
    }

    public Object buscarPorUsuario(long userId) {
        try {
            return entityManager().createQuery("from " + PersonaVulnerable.class.getName() + " where usuario_id = :userId")
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    public void actualizar(Object object) {

        if (object instanceof PersonaVulnerable) {
            PersonaVulnerable personaVulnerable = (PersonaVulnerable) object;
            personaVulnerable.setFechaModificacion(LocalDateTime.now());
            withTransaction(() -> { entityManager().merge(personaVulnerable);});
        } else {
            throw new IllegalArgumentException("El objeto proporcionado no es una instancia de PersonaVulnerable");
        }
    }

    @Override
    public void eliminar(Long id) {
        PersonaVulnerable personaVulnerable = find(PersonaVulnerable.class, id);
        personaVulnerable.setActivo(false);
        withTransaction(() -> { entityManager().merge(personaVulnerable);});
    }
}
