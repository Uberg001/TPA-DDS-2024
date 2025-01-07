package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Utils.EntityManagerProvider;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HeladeraRepository implements IRepository, WithSimplePersistenceUnit {

    @PersistenceContext
    private EntityManager entityManager = EntityManagerProvider.getEntityManager();


    public List<Heladera> findByDireccionLocalidad(String localidad) {

        String jpql = "SELECT h FROM Heladera h JOIN h.direccion d ON h.direccion.id = d.id  WHERE d.localidad.nombre = :localidad";
        Query query = entityManager().createQuery(jpql, Heladera.class);
        query.setParameter("localidad", localidad);

        return (List<Heladera>) query.getResultList();
    }

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Heladera.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return find(Heladera.class, id);
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
            Heladera heladera = (Heladera) object;
            heladera.setFechaModificacion(LocalDateTime.now());
            withTransaction(() -> {
                entityManager().merge(heladera);
            });
        } else {
            throw new IllegalArgumentException("El objeto proporcionado no es una instancia de Heladera");
        }
    }

    @Override
    public void eliminar(Long id) {
        Heladera heladera = find(Heladera.class, id);
        heladera.setActivo(false);
        withTransaction(() -> {
            entityManager().merge(heladera);
        });
    }

    //    public List<Heladera> getHeladerasPH(Long id) {
//        String sql = "SELECT h.* FROM Heladera h " +
//                "JOIN heladera_suscripcion hs ON h.id = hs.id_heladera " +
//                "JOIN suscripcion s ON hs.id_suscripcion = s.id " +
//                "JOIN persona_suscripcion ps ON s.id = ps.id_suscripcion " +
//                "JOIN personahumana p ON ps.id_persona = p.id " +
//                "WHERE p.id = "+id;
//        Query query = entityManager().createNativeQuery(sql, Heladera.class);
//        return (List<Heladera>) query.getResultList();
//    }
    public List<Heladera> getHeladerasPH(Long id) {
        String hql = "SELECT DISTINCT h.* " +
                "FROM heladera h " +
                "JOIN suscripcion s ON h.id = s.id_heladera " +
                "JOIN persona_suscripcion ps ON s.id = ps.id_suscripcion "+
                "JOIN personahumana pj ON ps.id_persona = pj.id "+
                "WHERE pj.id = "+id;
        Query query = entityManager().createNativeQuery(hql, Heladera.class);
        return (List<Heladera>) query.getResultList();
    }

    // Metodo que obtiene las heladeras a las que el pj  esta suscrito y/o tiene a cargo
    public List<Heladera> getHeladerasPJ(Long id) {
        List<Heladera> heladeras = new ArrayList<>();

        // Suscripcion
        String susc = "SELECT DISTINCT h.* " +
                "FROM heladera h " +
                "JOIN suscripcion s ON h.id = s.id_heladera " +
                "JOIN personaj_suscripcion ps ON s.id = ps.id_suscripcion "+
                "JOIN personajuridica pj ON ps.id_persona = pj.id "+
                "WHERE pj.id = "+id;
        Query querySusc = entityManager().createNativeQuery(susc, Heladera.class);
        heladeras.addAll((List<Heladera>) querySusc.getResultList());

        // Colaboracion
        String col = "SELECT DISTINCT h.* " +
                "FROM heladera h " +
                "JOIN colaboracion c ON h.id = c.id_heladera "+
                "WHERE c.id_persona_juridica = "+id+
                " AND c.tipo = 'HacerseCargoDeHeladeras';";
        Query queryCol = entityManager().createNativeQuery(col, Heladera.class);
        heladeras.addAll((List<Heladera>) queryCol.getResultList());

        return heladeras;
    }

    public List<Heladera> getHeladerasSuscripPJ (Long id){
        String susc = "SELECT DISTINCT h.* " +
                "FROM heladera h " +
                "JOIN suscripcion s ON h.id = s.id_heladera " +
                "JOIN personaj_suscripcion ps ON s.id = ps.id_suscripcion "+
                "JOIN personajuridica pj ON ps.id_persona = pj.id "+
                "WHERE pj.id = "+id;
        Query querySusc = entityManager().createNativeQuery(susc, Heladera.class);
        return (List<Heladera>) querySusc.getResultList();
    }

    public List<Heladera> getHeladerasACargoPJ(Long id){
        String col = "SELECT DISTINCT h.* " +
                "FROM heladera h " +
                "JOIN colaboracion c ON h.id = c.id_heladera "+
                "WHERE c.id_persona_juridica = "+id+
                " AND c.tipo = 'HacerseCargoDeHeladeras';";
        Query queryCol = entityManager().createNativeQuery(col, Heladera.class);
        return (List<Heladera>) queryCol.getResultList();
    }

    // Metodo que obtiene las heladeras a las que el pj no esta suscrito, ni la tiene a cargo
    public List<Heladera> getHeladerasSuscPJ(Long id){
        List<Heladera> heladeras = new ArrayList<>();

        String susc = "SELECT distinct h.* " +
                "FROM heladera h " +
                "LEFT JOIN suscripcion s ON h.id = s.id_heladera " +
                "LEFT JOIN personaj_suscripcion ps ON s.id = ps.id_suscripcion " +
                "LEFT JOIN personajuridica pj ON ps.id_persona = pj.id " +
                "WHERE h.id NOT IN ( " +
                "    SELECT h2.id " +
                "    FROM heladera h2 " +
                "    JOIN suscripcion s2 ON h2.id = s2.id_heladera " +
                "    JOIN personaj_suscripcion ps2 ON s2.id = ps2.id_suscripcion " +
                "    JOIN personajuridica pj2 ON ps2.id_persona = pj2.id " +
                "    WHERE pj2.id = "+id +
                ")"+
                "AND h.id NOT IN ( " +
                "    SELECT h3.id " +
                "    FROM heladera h3 " +
                "    JOIN colaboracion c2 ON h3.id = c2.id_heladera " +
                "    WHERE c2.tipo = 'HacerseCargoDeHeladeras' AND c2.id_persona_juridica = " +id +
                ")";
        Query query = entityManager().createNativeQuery(susc, Heladera.class);
        heladeras.addAll((List<Heladera>) query.getResultList());
        return heladeras;
    }

    // Metodo que obtiene las heladeras a las que el ph no esta suscrito
    public List<Heladera> getHeladerasSuscPH(Long id){
        List<Heladera> heladeras = new ArrayList<>();

        String susc = "SELECT distinct h.* " +
                "FROM heladera h " +
                "LEFT JOIN suscripcion s ON h.id = s.id_heladera " +
                "LEFT JOIN persona_suscripcion ps ON s.id = ps.id_suscripcion " +
                "LEFT JOIN personahumana pj ON ps.id_persona = pj.id " +
                "WHERE h.id NOT IN ( " +
                "    SELECT h2.id " +
                "    FROM heladera h2 " +
                "    JOIN suscripcion s2 ON h2.id = s2.id_heladera " +
                "    JOIN persona_suscripcion ps2 ON s2.id = ps2.id_suscripcion " +
                "    JOIN personahumana pj2 ON ps2.id_persona = pj2.id " +
                "    WHERE pj2.id = 22 " +
                ")";
        Query query = entityManager().createNativeQuery(susc, Heladera.class);
        heladeras.addAll((List<Heladera>) query.getResultList());
        return heladeras;
    }

    public List<Heladera> getHeladerasMapa(){
        String hql = "SELECT h FROM Heladera h " +
                "WHERE h.activa = true " +
                "AND h.activo = true";
        Query query = entityManager().createQuery(hql, Heladera.class);
        return (List<Heladera>) query.getResultList();
    }

    public List<Heladera> getHeladerasMapaTecnico(){
        String hql = "SELECT h FROM Heladera h " +
                "WHERE h.activo = true";
        Query query = entityManager().createQuery(hql, Heladera.class);
        return (List<Heladera>) query.getResultList();
    }
}
