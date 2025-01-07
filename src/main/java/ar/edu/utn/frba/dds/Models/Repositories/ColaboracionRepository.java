package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Utils.EntityManagerProvider;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

public class ColaboracionRepository implements IRepository, WithSimplePersistenceUnit {
    @PersistenceContext
    private EntityManager entityManager = EntityManagerProvider.getEntityManager();

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Colaboracion.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(Colaboracion.class, id);
    }

    @Override
    public void guardar(Object object) {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(object);
        tx.commit();
    }

    @Override
    public void actualizar(Object object) {
        withTransaction(() -> { entityManager.merge(object);});
    }

    @Override
    public void eliminar(Long id) {
        Colaboracion colaboracion = find(Colaboracion.class, id);
        colaboracion.setActivo(false);
        withTransaction(() -> { entityManager.merge(colaboracion);});
    }

    public boolean getSiHeladeraEsDePJ(Long heladeraId, Long personaJuridicaId) {
        String query = "SELECT COUNT(c) > 0 FROM Colaboracion c WHERE c.heladera.id = :heladeraId AND c.personaJuridica.id = :personaJuridicaId";
        return (Boolean) entityManager.createQuery(query)
                .setParameter("heladeraId", heladeraId)
                .setParameter("personaJuridicaId", personaJuridicaId)
                .getSingleResult();
    }
}
