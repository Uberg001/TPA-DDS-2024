package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Utils.EntityManagerProvider;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AlertaRepository implements IRepository, WithSimplePersistenceUnit {

    private EntityManager entityManager = EntityManagerProvider.getEntityManager();

    @Override
    public List buscarTodos() {
        return null;
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

    public List<Incidente> buscarAlertasHeladera(Long id){
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        return entityManager.createQuery("SELECT i FROM Incidente i WHERE i.heladera.id = :id", Incidente.class)
                .setParameter("id", id)
                .getResultList();
    }
}
