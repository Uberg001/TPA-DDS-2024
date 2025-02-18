package db;


import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContextTest implements SimplePersistenceTest {

    @Test
    void contextUp() {
        assertNotNull(entityManager());
    }

    @Test
    void contextUpWithTransaction() throws Exception {
        withTransaction(() -> {
        });
    }

    @Test
    void testQuery() {
        System.out.println(Incidente.class.getName());
        List<Incidente> incidentes = entityManager().createQuery("from " + Incidente.class.getName() + " where Incidente.heladera.id = 1", Incidente.class)
                //.setParameter("id", 1L) // Replace 1L with the actual id you want to test
                .getResultList();
        assertNotNull(incidentes);
        assertFalse(incidentes.isEmpty());
    }

    @Test
    void testNativeQuery() {
        String sql = "SELECT * FROM Incidente WHERE id_heladera = 1";
        List incidentes = entityManager().createNativeQuery(sql, Incidente.class)
                //.setParameter("id", 1L) // Replace 1L with the actual id you want to test
                .getResultList();
        assertNotNull(incidentes);
        assertFalse(incidentes.isEmpty());
    }

}
