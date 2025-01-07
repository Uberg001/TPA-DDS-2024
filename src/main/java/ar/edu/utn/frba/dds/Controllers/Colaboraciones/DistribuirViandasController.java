package ar.edu.utn.frba.dds.Controllers.Colaboraciones;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.HeladeraDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaHumanaDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.Services.DistribuirViandasService;
import ar.edu.utn.frba.dds.Services.HeladeraServiceImpl;
import ar.edu.utn.frba.dds.Services.PersonaHumanaServiceImpl;
import ar.edu.utn.frba.dds.Models.DTOs.DistribuirViandasDTOEntrada;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DistribuirViandasController {
    private DistribuirViandasService distribuirViandasService;

    public DistribuirViandasController(DistribuirViandasService distribuirViandasService) {
        this.distribuirViandasService = distribuirViandasService;
    }

    public void cargarColaboracion(Context ctx) {
        distribuirViandasService.distribuirViandas(setUpDTO(ctx));
        ctx.redirect("/");
    }

    private DistribuirViandasDTOEntrada setUpDTO(Context ctx) {
        long idHeladeraOrigen = Long.parseLong(ctx.formParam("heladera-origen"));
        long idHeladeraDestino = Long.parseLong(ctx.formParam("heladera-destino"));
        long idPH = ctx.sessionAttribute("id");
        Integer cantViandas = Integer.parseInt(ctx.formParam("cant-viandas"));
        String motivo = ctx.formParam("motivo-distribucion");
        LocalDate fechaRealizacion = LocalDate.now();
        DistribuirViandasDTOEntrada dto = new DistribuirViandasDTOEntrada();
        dto.setHeladeraOrigen(idHeladeraOrigen);
        dto.setHeladeraDestino(idHeladeraDestino);
        dto.setPersonaHumana(idPH);
        dto.setCantViandas(cantViandas);
        dto.setMotivo(motivo);
        dto.setFechaRealizacion(fechaRealizacion);
        return dto;
    }

    public void cargarColaboracionCSV(String[] columnas) {
        //TODO:  Arreglar esto, alguno que no sea Tobias ni Lucas
//        long cantViandas = Long.parseLong(columnas[7]);
//        String motivo = "null";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate fechaDonacion = LocalDate.parse(columnas[5], formatter);
//
//        //PersonaHumanaDTOSalida colaboradorDTO   = ServiceLocator.instanceOf(PersonaHumanaServiceImpl.class).getByDocumento(Long.parseLong(columnas[1]));
//        DistribuirViandasDTOEntrada dto = new DistribuirViandasDTOEntrada(null,
//                null,
//                null,
//                cantViandas,
//                motivo,
//                fechaDonacion);
//        this.distribuirViandasService.distribuirViandasCSV(dto);

    }

}
