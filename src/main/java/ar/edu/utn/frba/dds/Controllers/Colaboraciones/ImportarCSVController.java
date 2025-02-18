package ar.edu.utn.frba.dds.Controllers.Colaboraciones;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportarCSVController {

    private static final Logger logger = Logger.getLogger(ImportarCSVController.class.getName());

    public void recibirCSV(Context ctx) {
//        if(ctx.sessionAttribute("tipoPersona") != null) {
//            if(!ctx.sessionAttribute("tipoPersona").equals("administrador")) {
//                ctx.status(403).render("errors/403.hbs");
//                return;
//            }
//        } else {
//            ctx.redirect("/login");
//            return;
//        }
        UploadedFile csvFile = ctx.uploadedFile("csv-file");

        if (csvFile != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFile.content()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] columns = line.split(";");
                    if (columns.length < 9) {
                        logger.log(Level.WARNING, "CSV line does not have enough columns: " + line);
                        continue;
                    }

                    System.out.println(columns[0]); //Tipo DNI
                    System.out.println(columns[1]); //Numero
                    System.out.println(columns[2]); //Nombre
                    System.out.println(columns[3]); //Apellido
                    System.out.println(columns[4]); //Mail
                    System.out.println(columns[5]); //Fecha
                    System.out.println(columns[6]); //TipoColaboracion
                    System.out.println(columns[7]); //Cantidad
                    System.out.println(columns[8]); //Frecuencia

                    switch (columns[6]) {
                        case "DINERO":
                            ServiceLocator.instanceOf(DonarDineroController.class).cargarColaboracionCSV(ctx,columns);
                            break;
                        case "DONACION_VIANDAS":
                            ServiceLocator.instanceOf(DonarViandaController.class).cargarColaboracionCSV(columns);
                            break;
                        case "REDISTRIBUCION_VIANDAS":
                            ServiceLocator.instanceOf(DistribuirViandasController.class).cargarColaboracionCSV(columns);
                            break;
                        default:
                            logger.log(Level.WARNING, "Unknown collaboration type: " + columns[6]);
                            ctx.redirect("/");
                            return;
                    }
                }
                ctx.result("Archivo CSV procesado con éxito.");
                ctx.redirect("/");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error al leer el archivo CSV", e);
                ctx.status(404).result("Error al leer el archivo CSV: " + e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            ctx.status(400).result("No se recibió ningún archivo.");
        }
    }
}