package ar.edu.utn.frba.dds;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Reportes.GeneradorReporte;
import ar.edu.utn.frba.dds.Models.Domain.Reportes.Reporte;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;

public class GeneradorReporteTests {
    Reporte reporte = new Reporte();
    GeneradorReporte generadorReporte = new GeneradorReporte(reporte);

    List<Heladera> heladeras=new ArrayList<Heladera>();
    List<PersonaHumana> personasHumanas=new ArrayList<PersonaHumana>();
    List<Vianda> viandas=new ArrayList<Vianda>();

    @Test
    public void testGenerarReporte() {
        generadorReporte.generarReporte(heladeras, personasHumanas, viandas);
    }
}
