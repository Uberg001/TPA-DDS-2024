package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.DonarViandaDTOEntrada;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.Acceso;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.AccesoIngresar;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.SolicitudAbrir.Solicitud;
import ar.edu.utn.frba.dds.Models.Domain.Utils.EstadoVianda;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;
import ar.edu.utn.frba.dds.Models.Repositories.DonarViandaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaJuridicaRepository;
import io.javalin.http.Context;

import org.modelmapper.ModelMapper;

public class DonarViandaService {

    private DonarViandaRepository donarViandaRepository;

    public DonarViandaService(DonarViandaRepository donarViandaRepository){
        this.donarViandaRepository = donarViandaRepository;
    }

    public void donarVianda(Context ctx, DonarViandaDTOEntrada dto){
        DonarVianda donacion            = convertToEntity(dto);
        Solicitud solicitud             = new Solicitud();

        solicitud.setHeladera(donacion.getVianda().getHeladera());
        solicitud.connect();
        PersonaHumana personaDonadora   = (PersonaHumana) ServiceLocator.instanceOf(PersonaHumanaRepository.class).buscarPorUsuarioId(ctx.sessionAttribute("id"));
        solicitud.solicitar(personaDonadora.getTarjeta().getCodigo());

//        Acceso acceso                   = new AccesoIngresar();
//        acceso.setFechaSolicitud(Date.);
        //TODO: Habria que persistir las aperturas, las acciones, y los accesos (aaaaaaaaaaaaaa)
        //Cargo la colaboracion al colaborador
        actualizarPuntaje(ctx, donacion);

        donarViandaRepository.guardar(donacion);
    }

    public void donarViandaCSV(DonarViandaDTOEntrada dto){
        DonarVianda donacion = convertToEntityCSV(dto);
        donarViandaRepository.guardar(donacion);
    }

    public DonarVianda convertToEntity(DonarViandaDTOEntrada dto) {
        ModelMapper modelMapper = new ModelMapper();
        long idColaborador = dto.getColaborador().getId();
        PersonaHumana colaborador = (PersonaHumana) ServiceLocator.instanceOf(PersonaHumanaRepository.class).buscar(idColaborador);

        Vianda vianda = new Vianda(dto.getComida(),
                dto.getFechaCaducidad(),
                colaborador,
                dto.getHeladera(),
                dto.getEstado().equals(EstadoVianda.ENTREGADA),
                null);
        return new DonarVianda(dto.getFechaDonacion(), vianda);
    }

    public DonarVianda convertToEntityCSV(DonarViandaDTOEntrada dto){
        ModelMapper modelMapper     = new ModelMapper();
        //PersonaHumana personaHumana = modelMapper.map(dto.getColaborador(), PersonaHumana.class);

        Vianda vianda               = new Vianda(dto.getComida(),
                dto.getFechaCaducidad(),
                null,
                dto.getHeladera(),
                dto.getEstado().equals(EstadoVianda.ENTREGADA),
                null);
        return new DonarVianda(dto.getFechaDonacion(),vianda);
    }

    public void actualizarPuntaje(Context ctx, DonarVianda colaboracion) {
        long idColaborador = ctx.sessionAttribute("id");
        String tipoPersona = ctx.sessionAttribute("tipoPersona");

        Puntaje puntaje = new Puntaje();
        if (tipoPersona.equals("humana")) {
            PersonaHumana colaborador = (PersonaHumana) ServiceLocator.instanceOf(PersonaHumanaRepository.class).buscarPorUsuarioId(idColaborador);

            puntaje.setPuntaje(colaboracion.calcularPuntaje() + colaborador.getPuntaje().getPuntaje());
            colaborador.setPuntaje(puntaje);
            ServiceLocator.instanceOf(PersonaHumanaRepository.class).actualizar(colaborador);
        } else if (tipoPersona.equals("juridica")) {
            PersonaJuridica colaborador = (PersonaJuridica) ServiceLocator.instanceOf(PersonaJuridicaRepository.class).buscarPorUsuario(idColaborador);

            puntaje.setPuntaje(colaboracion.calcularPuntaje() + colaborador.getPuntaje().getPuntaje());
            colaborador.setPuntaje(puntaje);
            ServiceLocator.instanceOf(PersonaJuridicaRepository.class).actualizar(colaborador);
        }
    }
}
