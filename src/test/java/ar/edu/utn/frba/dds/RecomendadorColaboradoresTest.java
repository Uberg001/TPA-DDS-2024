package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Map;
import org.junit.jupiter.api.Test;

import ar.edu.utn.frba.dds.Controllers.RecomendadorColaboradoresController;


public class RecomendadorColaboradoresTest {
    RecomendadorColaboradoresController recomendadorColaboradores = new RecomendadorColaboradoresController();

    @Test
    public void testGetColaboradoresRecomendados() {
        try {
            //List<Object> colaboradores = recomendadorColaboradores.getColaboradoresRecomendados(100.0, 10, 0, 10, "puntos,desc");
            //assertEquals(new ArrayList<>(), colaboradores);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
