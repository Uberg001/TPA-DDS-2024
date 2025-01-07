package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.DTOs.IncidenteDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Utils.EntityManagerProvider;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

public class IncidenteRepository implements IRepository, WithSimplePersistenceUnit {
    //private EntityManager entityManager = EntityManagerProvider.getEntityManager();


    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Incidente.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(Incidente.class, id);
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
        withTransaction(() -> {
            entityManager().merge(object);
        });
    }

    @Override
    public void eliminar(Long id) {
        Incidente incidente = find(Incidente.class, id);
        incidente.setActivo(false);
        withTransaction(() -> {
            entityManager().merge(incidente);
        });
    }

    public List<Incidente> buscarPorHeladera(Long id) {
        return entityManager().createNativeQuery("SELECT * " +
                "FROM Incidente " +
                "WHERE id_heladera= " + id +
                " AND activo = true " +
                "AND fecha >= NOW() - INTERVAL 1 MONTH", Incidente.class).getResultList();
    }

    public List<Incidente> buscarPorHeladeraPH(Long idHeladera, Long idPH) {
        String hql = "  SELECT i.* " +
                "FROM incidente i " +
                "JOIN heladera h ON i.id_heladera = h.id " +
                "JOIN suscripcion s ON h.id = s.id_heladera " +
                "JOIN persona_suscripcion ps ON s.id = ps.id_suscripcion " +
                "JOIN personahumana ph ON ps.id_persona = ph.id " +
                "WHERE ph.id = "+idPH +
                "  AND i.tipoIncidente = s.tipo "+
                "AND h.id = " + idHeladera +
                " AND i.activo = true ";
        Query query = entityManager().createNativeQuery(hql, Incidente.class);
        return (List<Incidente>) query.getResultList();
    }
    public List<Incidente> buscarPorHeladeraPJ(Long idHeladera, Long idPJ) {
        String hql = "  SELECT i.* " +
                "FROM incidente i " +
                "JOIN heladera h ON i.id_heladera = h.id " +
                "JOIN suscripcion s ON h.id = s.id_heladera " +
                "JOIN personaj_suscripcion ps ON s.id = ps.id_suscripcion " +
                "JOIN personajuridica ph ON ps.id_persona = ph.id " +
                "WHERE ph.id = "+idPJ +
                "  AND i.tipoIncidente = s.tipo "+
                "AND h.id = " + idHeladera +
                " AND i.activo = true ";
        Query query = entityManager().createNativeQuery(hql, Incidente.class);
        return (List<Incidente>) query.getResultList();
    }


}