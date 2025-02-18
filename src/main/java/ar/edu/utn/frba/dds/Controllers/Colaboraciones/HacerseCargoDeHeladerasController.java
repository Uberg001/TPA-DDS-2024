package ar.edu.utn.frba.dds.Controllers.Colaboraciones;

import ar.edu.utn.frba.dds.Models.DTOs.HacerseCargoDeHeladeraDTOEntrada;
import ar.edu.utn.frba.dds.Services.HacerseCargoDeHeladeraService;
import ar.edu.utn.frba.dds.Services.PersonaJuridicaServiceImpl;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HacerseCargoDeHeladerasController {

    private HacerseCargoDeHeladeraService hacerseCargoDeHeladeraService;
    private PersonaJuridicaServiceImpl personaJuridicaService;

    public HacerseCargoDeHeladerasController(HacerseCargoDeHeladeraService hacerseCargoDeHeladeraService, PersonaJuridicaServiceImpl personaJuridicaService) {
        this.hacerseCargoDeHeladeraService = hacerseCargoDeHeladeraService;
        this.personaJuridicaService = personaJuridicaService;
    }

    public void cargarColaboracion(Context ctx) {

        this.hacerseCargoDeHeladeraService.cargarColaboracion(ctx, setUpDTO(ctx));
        ctx.redirect("/");

    }

    private HacerseCargoDeHeladeraDTOEntrada setUpDTO(Context ctx) {

        String calle = ctx.formParam("calle");
        Integer altura = Integer.valueOf(ctx.formParam("altura"));
        String localidad = ctx.formParam("localidad");
        String provincia = ctx.formParam("provincia");
        String nombre = ctx.formParam("nombre");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaInstalacion = LocalDate.parse(ctx.formParam("fecha-instalacion"), formatter);
        Long modelo = Long.valueOf(ctx.formParam("modelo"));
        Long idUsuario = ctx.sessionAttribute("id");
        Long idPJ = ctx.sessionAttribute("idPJ");

        HacerseCargoDeHeladeraDTOEntrada dto = new HacerseCargoDeHeladeraDTOEntrada(calle, altura, localidad, provincia, null, null, nombre, fechaInstalacion, modelo, ctx.sessionAttribute("id"));

        return dto;
    }

}
