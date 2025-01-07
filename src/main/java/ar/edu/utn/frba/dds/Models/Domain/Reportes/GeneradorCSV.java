package ar.edu.utn.frba.dds.Models.Domain.Reportes;

import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Models.Domain.Incidentes.TipoIncidente;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GeneradorCSV {
    public static void main(String[] args) {
        List<Heladera> heladeras = crearHeladeras();
        List<Incidente> incidentes = crearIncidentes(heladeras);
        asociarIncidentes(heladeras, incidentes);
        generarCSV(incidentes);
        imprimirDetalles(heladeras);
    }

    private static List<Heladera> crearHeladeras() {
        List<Heladera> heladeras = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            heladeras.add(new Heladera("hela" + i));
        }
        return heladeras;
    }

    private static List<Incidente> crearIncidentes(List<Heladera> heladeras) {
        List<LocalDateTime> fechas = List.of(
                LocalDateTime.of(2024, 7, 7, 12, 30),
                LocalDateTime.of(2024, 5, 4, 1, 30),
                LocalDateTime.of(2024, 1, 1, 2, 30),
                LocalDateTime.of(2024, 7, 4, 12, 55),
                LocalDateTime.of(2024, 6, 5, 12, 40)
        );

        List<Incidente> incidentes = new ArrayList<>();
        for (int i = 0; i < fechas.size(); i++) {
            //incidentes.add(new Incidente(fechas.get(i), heladeras.get(i % heladeras.size()), TipoIncidente.ALERTA_TEMPERATURA, null, null));
        }
        return incidentes;
    }

    private static void asociarIncidentes(List<Heladera> heladeras, List<Incidente> incidentes) {
        heladeras.get(0).agregarIncidente(incidentes.get(0));
        heladeras.get(1).agregarIncidente(incidentes.get(1));
        heladeras.get(2).agregarIncidente(incidentes.get(2));
        heladeras.get(3).agregarIncidente(incidentes.get(3));
        heladeras.get(3).agregarIncidente(incidentes.get(4));
    }

    private static void generarCSV(List<Incidente> incidentes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("incidentes.csv"))) {
            writer.write("FechaHora,Heladera,TipoIncidente,Detalle1,Detalle2");
            writer.newLine();
            for (Incidente incidente : incidentes) {
                writer.write(String.format("%s,%s,%s,%s,%s",
                        incidente.getFecha(),
                        incidente.getHeladera() != null ? incidente.getHeladera().getNombre() : "",
                        incidente.getTipoIncidente(),
                        incidente.getFallaTecnica() != null ? incidente.getFallaTecnica() : "",
                        incidente.getNotificacion() != null ? incidente.getNotificacion() : ""
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void imprimirDetalles(List<Heladera> heladeras) {
        System.out.println("\nDetalles de los incidentes por heladera:");
        for (Heladera heladera : heladeras) {
            System.out.println("Heladera: " + heladera.getNombre());
            for (Incidente incidente : heladera.getIncidentes()) {
                System.out.println(String.format("  FechaHora: %s, TipoIncidente: %s, Detalle1: %s, Detalle2: %s",
                        incidente.getFecha(),
                        incidente.getTipoIncidente(),
                        incidente.getFallaTecnica() != null ? incidente.getFallaTecnica() : "",
                        incidente.getNotificacion() != null ? incidente.getNotificacion() : ""
                ));
            }
            System.out.println();
        }
    }
}