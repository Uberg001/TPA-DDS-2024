package ar.edu.utn.frba.dds.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Reportes.GeneradorReporte;
import ar.edu.utn.frba.dds.Models.Domain.Reportes.Reporte;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;
import ar.edu.utn.frba.dds.Models.Repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.ViandaRepository;
import io.javalin.http.Context;

public class ReporteController {
    private static final Logger logger = LoggerFactory.getLogger(ReporteController.class);
    private final ScheduledExecutorService scheduler;
    Reporte reporte = new Reporte();
    GeneradorReporte generadorReporte = new GeneradorReporte(reporte);
    List<Heladera> heladeras=new ArrayList<Heladera>();
    List<PersonaHumana> personasHumanas=new ArrayList<PersonaHumana>();
    List<Vianda> viandas=new ArrayList<Vianda>();
    List<String> reportesPaths = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public ReporteController() {
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.heladeras = (List<Heladera>)ServiceLocator.instanceOf(HeladeraRepository.class).buscarTodos();
        this.personasHumanas=(List<PersonaHumana>) ServiceLocator.instanceOf(PersonaHumanaRepository.class).buscarTodos();
        this.viandas=(List<Vianda>) ServiceLocator.instanceOf(ViandaRepository.class).buscarTodos();
        logger.info("ReporteController inicializado con {} heladeras, {} personas humanas y {} viandas.", heladeras.size(), personasHumanas.size(), viandas.size());
    }

    // Método para iniciar la generación de reportes
    public void iniciarGeneracionReportes() {
        logger.info("Iniciando la generación de reportes...");
        scheduler.scheduleAtFixedRate(() -> {
            logger.info("Generando reporte...");
            generadorReporte.generarReporteBeta(heladeras, personasHumanas, viandas).run();
            logger.info("Reporte generado.");
        }, 0, 5, TimeUnit.MINUTES);
    }

    // Método para iniciar la actualización de la lista de reportes
    public void iniciarActualizacionReportes() {
        scheduler.scheduleAtFixedRate(this::actualizarReportes, 0, 5, TimeUnit.MINUTES);
    }

    /// Método para actualizar la lista de reportes
    private void actualizarReportes() {
        logger.info("Actualizando lista de reportes...");
        String directoryPath = "src/main/resources/public/reportes/";
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            reportesPaths = paths
                .filter(Files::isRegularFile)
                .map(Path::getFileName) // Obtener solo el nombre del archivo
                .map(Path::toString)
                .filter(path -> path.endsWith(".pdf"))
                .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para manejar la solicitud del cliente
    public void index(Context ctx) {
        if(ctx.sessionAttribute("tipoPersona") != null) {
            if(!ctx.sessionAttribute("tipoPersona").equals("administrador")) {
                ctx.status(403).render("errors/403.hbs");
                return;
            }
        } else {
            ctx.redirect("/login");
            return;
        }

        ctx.render("reportes/mostrarReportes.hbs", Map.of("reportes", reportesPaths));
    }
}
