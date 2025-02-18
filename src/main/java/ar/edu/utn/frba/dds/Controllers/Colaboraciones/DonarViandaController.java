package ar.edu.utn.frba.dds.Controllers.Colaboraciones;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.DonarDineroDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.DonarViandaDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.HeladeraDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaHumanaDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Estado;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.SolicitudAbrir.Solicitud;
import ar.edu.utn.frba.dds.Models.Domain.Utils.EstadoVianda;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Frecuencia;
import ar.edu.utn.frba.dds.Models.Repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.Services.DonarViandaService;
import ar.edu.utn.frba.dds.Services.HeladeraServiceImpl;
import ar.edu.utn.frba.dds.Services.PersonaHumanaServiceImpl;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ar.edu.utn.frba.dds.Models.Domain.Utils.EstadoVianda.ENTREGADA;

public class DonarViandaController {

    private DonarViandaService donarViandaService;

    public DonarViandaController(DonarViandaService donarViandaService) {
        this.donarViandaService = donarViandaService;
    }

    public void cargarColaboracion(Context ctx) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaCaducidad    = LocalDate.parse(ctx.formParam("fecha-caducidad"), formatter);
        LocalDate fechaDonacion     = LocalDate.parse(ctx.formParam("fecha-donacion"), formatter);
        String comida               = ctx.formParam("tipo-comida");
        EstadoVianda estado         = EstadoVianda.valueOf(ctx.formParam("estado").toUpperCase(Locale.ROOT));
        long idHeladera             = Long.parseLong(ctx.formParam("heladera"));
        long idColaborador          = ctx.sessionAttribute("id");

        Integer calorias = null;
        if(ctx.formParam("calorias") != null || !ctx.formParam("calorias").isEmpty()){
            calorias = Integer.valueOf(ctx.formParam("calorias"));
        }
        Integer peso = null;
        if(ctx.formParam("peso") != null || !ctx.formParam("peso").isEmpty()){
            peso = Integer.valueOf(ctx.formParam("peso"));
        }

        PersonaHumanaDTOSalida colaboradorDTO   = ServiceLocator.instanceOf(PersonaHumanaServiceImpl.class).getByUserId(idColaborador);
        //HeladeraDTOSalida heladeraDTO           = ServiceLocator.instanceOf(HeladeraServiceImpl.class).getById(idHeladera);
        Heladera heladera                       = (Heladera) ServiceLocator.instanceOf(HeladeraRepository.class).buscar(idHeladera);
        DonarViandaDTOEntrada dto               = new DonarViandaDTOEntrada(fechaCaducidad, fechaDonacion, comida,estado, heladera, colaboradorDTO, calorias, peso);

        this.donarViandaService.donarVianda(ctx,dto);
        ctx.redirect("/");
    }

    public void cargarColaboracionCSV(String[] columnas) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaCaducidad    = null;
        LocalDate fechaDonacion     = LocalDate.parse(columnas[5], formatter);
        String comida               = null;
        EstadoVianda estado         = ENTREGADA;
        Integer calorias            = 0;
        Integer peso                = 0;

        //PersonaHumanaDTOSalida colaboradorDTO = ServiceLocator.instanceOf(PersonaHumanaServiceImpl.class).getByDocumento(Long.parseLong(columnas[1]));
        DonarViandaDTOEntrada dto = new DonarViandaDTOEntrada(fechaCaducidad, fechaDonacion, comida,estado, null, new PersonaHumanaDTOSalida(), calorias, peso);

        this.donarViandaService.donarViandaCSV(dto);
    }
}
