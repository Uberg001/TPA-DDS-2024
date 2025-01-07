package ar.edu.utn.frba.dds.Controllers.Colaboraciones;

import ar.edu.utn.frba.dds.Models.DTOs.DonarDineroDTOEntrada;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Frecuencia;
import ar.edu.utn.frba.dds.Services.DonarDineroService;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DonarDineroController {

    public DonarDineroController(DonarDineroService donarDineroService) {
        this.donarDineroService = donarDineroService;
    }

    private static final Logger logger = Logger.getLogger(DonarDineroController.class.getName());
    private DonarDineroService donarDineroService;



    public void cargarColaboracion(Context ctx) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha = LocalDate.parse(Objects.requireNonNull(ctx.formParam("fecha")), formatter);
        Integer monto = Integer.valueOf(Objects.requireNonNull(ctx.formParam("monto")));
        Frecuencia frecuencia = Frecuencia.valueOf(Objects.requireNonNull(ctx.formParam("frecuencia")).toUpperCase(Locale.ROOT));
        long idColaborador = ctx.sessionAttribute("id");
        String tipoPersona = ctx.sessionAttribute("tipoPersona");
        DonarDineroDTOEntrada dto = new DonarDineroDTOEntrada(fecha, monto, frecuencia, idColaborador, TipoActor.valueOf(tipoPersona.toUpperCase(Locale.ROOT)));
        this.donarDineroService.donarDinero(dto);
        ctx.redirect("/");
    }

    public void cargarColaboracionCSV(Context ctx,String[] columnas) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fecha = LocalDate.parse(columnas[5], formatter);
            int monto = Integer.parseInt(columnas[7].trim().replace("\"", ""));
            String frecuenciaStr = columnas[8].trim().toUpperCase(Locale.ROOT);
            Frecuencia frecuencia = Frecuencia.valueOf(frecuenciaStr);
            DonarDineroDTOEntrada dto = new DonarDineroDTOEntrada(fecha, monto, frecuencia,null,TipoActor.ADMINISTRADOR);
            this.donarDineroService.donarDinero(dto);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid number format for cantidad: " + columnas[7], e);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Invalid value for frecuencia: " + columnas[8], e);
        }
    }
}