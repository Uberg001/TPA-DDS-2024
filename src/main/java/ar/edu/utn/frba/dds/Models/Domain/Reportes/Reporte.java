package ar.edu.utn.frba.dds.Models.Domain.Reportes;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.AccesoIngresar;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.AccesoRetirar;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Reporte extends Persistente {

    @Column
    private String path;

    public List<String> fallasPorHeladera(List<Heladera> heladeras) {
        List<String> reporte = new ArrayList<>();
        heladeras.stream().forEach(heladera -> {
            reporte.add("Heladera: " + heladera.getId());
            reporte.add("Fallas: " + heladera.getIncidentes().size());
        });
        return reporte;
    }

    public List<String> ingresoYRetiroDeViandasPorHeladera(List<Heladera> heladeras) {
        List<String> reporte = new ArrayList<>();
        heladeras.stream().forEach(heladera -> {
            reporte.add("Heladera: " + heladera.getId());
            reporte.add("Ingresos: " + heladera.getAccesosHistoricos().stream().filter(acceso->acceso instanceof AccesoIngresar).count());
            reporte.add("Retiros: " + heladera.getAccesosHistoricos().stream().filter(acceso -> acceso instanceof AccesoRetirar).count());
        });
        return reporte;
    }

    public List<String> viandasPorColaborador(List<PersonaHumana> personasHumanas, List<Vianda> viandas) {
        List<String> reporte = new ArrayList<>();
        personasHumanas.forEach(persona -> {
            String nombreCompleto = persona.getNombre() + " " + persona.getApellido();
            reporte.add("Colaborador: " + nombreCompleto);

            Long cantViandas=viandas.stream()
            .filter(vianda->vianda.getColaborador().getId()==persona.getId()).count();

            reporte.add("Viandas: " + cantViandas);
        });
        return reporte;
    }
}